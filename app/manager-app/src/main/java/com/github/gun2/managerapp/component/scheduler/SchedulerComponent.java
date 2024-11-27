package com.github.gun2.managerapp.component.scheduler;

import com.github.gun2.managerapp.controller.rest.SchedulerRestController;
import com.github.gun2.managerapp.controller.rest.SysInfoRestController;
import com.github.gun2.managerapp.config.RestTemplateConfig;
import com.github.gun2.managerapp.dto.HttpResponseDto;
import com.github.gun2.managerapp.dto.JobBodyDto;
import com.github.gun2.managerapp.dto.JobDto;
import com.github.gun2.managerapp.dto.ScheduleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * <b>스케줄러 컴포넌트</b>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SchedulerComponent {
    /**
     * <b>현재 실행해야하는 스케줄러 정보</b>
     */
    private final ConcurrentReferenceHashMap<Long, SchedulerInfo> schedulerInfoMap = new ConcurrentReferenceHashMap<>();

    private final SysInfoRestController sysInfoRestController;
    private final SchedulerRestController schedulerRestController;
    private final SchedulerLogBlockingQueue schedulerLogBlockingQueue;

    @Value("${app.default.value.scheduler-delay:100}")
    private int defaultSchedulerDelay;

    @Value("${app.default.value.task-request-delay:100}")
    private int defaultTaskRequestDelay;

    /**
     * <b>동작중인 스케줄러 중 job id와 관련되어있는 스케줄 반환</b>
     * @param jobId 업무ID
     * @return
     */
    public List<ScheduleDto> getRelatedScheduleListFromJob(Long jobId) {
        return schedulerInfoMap.values().stream().map(SchedulerInfo::getScheduleDto).filter(
                scheduleDto -> scheduleDto.getJobIdList().stream().anyMatch(jobId::equals)
        ).toList();
    }

    /**
     * <b>업무 정보 업데이트</b>
     *
     * @param updatedJobDto 업데이트된 업무
     */
    public void updateJob(JobDto updatedJobDto) {
        List<ScheduleDto> relatedScheduleList = getRelatedScheduleListFromJob(updatedJobDto.getId());
        relatedScheduleList.stream().forEach(scheduleDto -> {
            List<JobDto> jobDtoList = scheduleDto.getJobList().stream().map(job -> {
                if (job.getId().equals(updatedJobDto.getId())) {
                    return updatedJobDto;
                } else {
                    return job;
                }
            }).toList();
            scheduleDto.setJobList(jobDtoList);
            updateSchedule(scheduleDto);
        });
    }

    /**
     * <b>스케줄 정보 업데이트</b>
     *
     * @param scheduleDto 업데이트할 스케줄
     */
    public void updateSchedule(ScheduleDto scheduleDto) {
        if (scheduleDto.isRun()) {
            if (schedulerInfoMap.containsKey(scheduleDto.getId())) {
                //schedule 정보만 업데이트
                SchedulerInfo schedulerInfo = schedulerInfoMap.get(scheduleDto.getId());
                schedulerInfo.setScheduleDto(scheduleDto);
                schedulerInfo.getRunThread().interrupt();
            } else {
                //새로 만들기
                schedulerInfoMap.put(scheduleDto.getId(), new SchedulerInfo(scheduleDto));
            }
        }
        this.run();
    }

    /**
     * <b>job 삭제에 따른 스케줄러 업데이트</b>
     * @param jobId 업데이트된 job id
     */
    public void deleteJob(Long jobId) {
        List<ScheduleDto> relatedScheduleList = getRelatedScheduleListFromJob(jobId);
        relatedScheduleList.stream().forEach(scheduleDto -> {
            List<JobDto> jobDtoList = scheduleDto.getJobList().stream().filter(job -> job.getId() != jobId).toList();
            scheduleDto.setJobList(jobDtoList);
            scheduleDto.setJobIdList(jobDtoList.stream().map(JobDto::getId).toList());
            updateSchedule(scheduleDto);
        });
    }

    /**
     * <b>스케줄과 관련된 스케줄러 삭제</b>
     *
     * @param scheduleId 삭제할 스케줄 ID
     */
    public void deleteSchedule(Long scheduleId) {
        if (schedulerInfoMap.containsKey(scheduleId)) {
            schedulerInfoMap.get(scheduleId).getRunThread().interrupt();
            schedulerInfoMap.remove(scheduleId);
            schedulerRestController.deleteScheduleInfo(scheduleId);
        }
    }

    /**
     * <b>스케줄러 정보 가져오기</b>
     *
     * @return 스케줄러 정보 map
     */
    public ConcurrentReferenceHashMap<Long, SchedulerInfo> getSchedulerInfoMap() {
        return schedulerInfoMap;
    }

    /**
     * <b>컴폰넌트 초기화</b>
     */
    public void init(List<ScheduleDto> scheduleDtoList) {
        //List<ScheduleDto> scheduleDtoList = scheduleService.findByRun(true);
        scheduleDtoList.forEach(scheduleDto -> {
            schedulerInfoMap.put(scheduleDto.getId(), new SchedulerInfo(scheduleDto));
        });
    }

    public void run() {
        log.info("RestSchedulerComponent run!!!");
        for (Long id : schedulerInfoMap.keySet()) {
            final SchedulerInfo info = schedulerInfoMap.get(id);
            if (!info.getRunThread().isAlive() || info.getRunThread().isInterrupted()) {
                schedulerRestController.insertScheduleInfo(info);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        RestTemplate restTemplate = RestTemplateConfig.createElseGetNull();
                        while (!Thread.currentThread().isInterrupted()) {

                            ScheduleDto scheduleDto = info.getScheduleDto();
                            scheduleDto.getJobList().forEach(job -> {

                                jobRequest(job, info, restTemplate);
                            });
                            //실행 리스트에 해당 스케줄이 없으면 종료
                            if (!schedulerInfoMap.containsKey(scheduleDto.getId())) {
                                break;
                            }
                            scheduleDelaySleep(scheduleDto);
                        }
                    }
                });
                thread.start();
                info.setRunThread(thread);
            }
        }
    }

    private void jobRequest(JobDto job, SchedulerInfo info, RestTemplate restTemplate) {
        List<JobBodyDto> usableBody = job.getJobBodyList()
                .stream()
                .filter(body -> body.isUsable())
                .toList();
        HttpHeaders headers = new HttpHeaders();
        job.getJobHeaderList().stream()
                .filter(header ->
                        StringUtils.isNoneBlank(header.getKeyName()) && header.isUsable())
                .forEach(header -> {
                    headers.add(header.getKeyName(), header.getValue());
                });

        usableBody.forEach(body -> {
            requestBody(job, body, headers, info, restTemplate, defaultTaskRequestDelay);
        });
    }

    /**
     * <p>스케줄에 설정된 딜레이 만큼 sleep을 수행한다.</p>
     * @param scheduleDto
     */
    private void scheduleDelaySleep(ScheduleDto scheduleDto) {
        try {
            Integer scheduleDelay = scheduleDto.getDelay();
            if (scheduleDelay == null || scheduleDelay < defaultSchedulerDelay) {
                scheduleDelay = defaultSchedulerDelay;
            }
            log.trace("schedule id: {}, sleep : {}",
                    scheduleDto.getId()
                    , scheduleDelay);
            Thread.currentThread().sleep(scheduleDelay);
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }catch (Exception e) {
            log.error("scheduleDelaySleep : {}", e);
        }
    }

    /**
     * <p>request를 수행한다.</p>
     * @param job
     * @param body
     * @param info
     * @param restTemplate
     * @param defaultTaskRequestDelay
     */
    private void requestBody(JobDto job,
                             JobBodyDto body,
                             HttpHeaders headers,
                             SchedulerInfo info,
                             RestTemplate restTemplate,
                             Integer defaultTaskRequestDelay) {
        long startTime = System.currentTimeMillis();
        HttpResponseDto httpResponseDto = new HttpResponseDto();
        try {
            HttpEntity<String> entity = new HttpEntity<String>(body.getBody(), headers);

            String url = job.getUrl();
            HttpMethod httpMethod = HttpMethod.valueOf(job.getMethod().name());

            httpResponseDto.setRequest(entity,url, job.getMethod().name());

            ResponseEntity<String> response = restTemplate.exchange(url, httpMethod, entity, String.class);
            long time = System.currentTimeMillis() - startTime;
            httpResponseDto.setTime(time);
            recordSucceedRequest(httpResponseDto, response, info);
        } catch (Exception e) {
            //log.error("Exception : {}", e);
            long time = System.currentTimeMillis() - startTime;
            httpResponseDto.setTime(time);
            recordFailedRequest(httpResponseDto, e, info);
        }
        try {
            long time = System.currentTimeMillis() - startTime;
            info.setLastTime(time);
            Integer afterDelay = body.getAfterDelay();
            if (afterDelay == null || afterDelay < defaultTaskRequestDelay) {
                afterDelay = defaultTaskRequestDelay;
            }
            Thread.currentThread().sleep(afterDelay);
        } catch (InterruptedException e){
            log.trace("requestBody : {}", e);
            Thread.currentThread().interrupted();
        }catch (Exception e) {
            log.error("requestBody : {}", e);
        }
    }

    /**
     * <p>요청 성공 정보 저장</p>
     * @param httpResponseDto
     * @param response
     * @param info
     */
    private void recordSucceedRequest(HttpResponseDto httpResponseDto, ResponseEntity<String> response, SchedulerInfo info) {
        httpResponseDto.setResponse(response);
        schedulerLogBlockingQueue.addSuccess(info, httpResponseDto);
        //info.recordSucceedResponse(httpResponseDto);
        info.getSuccessCount().incrementAndGet();
        sysInfoRestController.increaseSuccessNumber();
        schedulerRestController.updateScheduleInfo(info);
    }

    /**
     * <p>요청 실패 정보 저장</p>
     * @param httpResponseDto
     * @param e
     * @param info
     * @param <T>
     */
    private <T extends Exception> void recordFailedRequest(HttpResponseDto httpResponseDto, T e, SchedulerInfo info) {
        httpResponseDto.setErrorResponse(e);
        schedulerLogBlockingQueue.addFailure(info, httpResponseDto);
        //info.recordFailureResponse(httpResponseDto);
        info.getFailureCount().incrementAndGet();
        sysInfoRestController.increaseFailureNumber();
        schedulerRestController.updateScheduleInfo(info);
    }

    /**
     * <b>재살행</b>
     *
     * @param schedulerInfo
     */
    public void restartSchedule(SchedulerInfo schedulerInfo) {
        log.info("scheduler id : {} restart....", schedulerInfo.getScheduleDto().getId());
        schedulerInfo.getRunThread().interrupt();
        this.run();
    }
}
