package com.gun2.restest.component.scheduler;

import com.gun2.restest.constant.GlobalValue;
import com.gun2.restest.controller.rest.SchedulerRestController;
import com.gun2.restest.controller.rest.SysInfoRestController;
import com.gun2.restest.dto.JobBodyDto;
import com.gun2.restest.dto.JobDto;
import com.gun2.restest.dto.ScheduleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private final RestTemplate restTemplate;

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
                        while (!Thread.currentThread().isInterrupted()) {
                            ScheduleDto scheduleDto = info.getScheduleDto();
                            scheduleDto.getJobList().forEach(job -> {

                                List<JobBodyDto> usableBody = job.getJobBodyList()
                                        .stream()
                                        .filter(body -> body.isUsable())
                                        .toList();
                                usableBody.forEach(body -> {
                                    try {
                                        //TODO : 구상 잡히면 리펙토링 진행
                                        log.info("[TEST] schedule id : {}, job id : {}, job url : {}, body : {}",
                                                scheduleDto.getId(),
                                                job.getId(),
                                                job.getUrl(),
                                                body.getBody());

                                        HttpHeaders headers = new HttpHeaders();
                                        job.getJobHeaderList().forEach(header -> {
                                            headers.add(header.getKeyName(), header.getValue());
                                        });

                                        HttpEntity<String> entity = new HttpEntity<String>(body.getBody(), headers);

                                        String url = job.getUrl();
                                        HttpMethod httpMethod = HttpMethod.valueOf(job.getMethod().name());

                                        ResponseEntity response = restTemplate.exchange(url, httpMethod, entity, String.class);

                                        log.info("tostring : {}", response.toString());
                                        log.info("getbody : {}", response.getBody());

                                        info.getSuccessCount().incrementAndGet();
                                        sysInfoRestController.increaseSuccessNumber();
                                        schedulerRestController.updateScheduleInfo(info);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        info.getFailureCount().incrementAndGet();
                                        sysInfoRestController.increaseFailureNumber();
                                        schedulerRestController.updateScheduleInfo(info);
                                    }
                                    try {
                                        Integer afterDelay = body.getAfterDelay();
                                        if (afterDelay == null || afterDelay < GlobalValue.AFTER_BODY_MIN_DELAY) {
                                            afterDelay = GlobalValue.AFTER_BODY_MIN_DELAY;
                                        }
                                        Thread.currentThread().sleep(afterDelay);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Thread.currentThread().interrupted();
                                    }
                                });
                            });
                            //실행 리스트에 해당 스케줄이 없으면 종료
                            if (!schedulerInfoMap.containsKey(scheduleDto.getId())) {
                                break;
                            }
                            try {
                                Integer scheduleDelay = scheduleDto.getDelay();
                                if (scheduleDelay == null || scheduleDelay < GlobalValue.SCHEDULE_MIN_DELAY) {
                                    scheduleDelay = GlobalValue.SCHEDULE_MIN_DELAY;
                                }
                                log.info("schedule id: {}, sleep : {}",
                                        scheduleDto.getId()
                                        , scheduleDelay);
                                Thread.currentThread().sleep(scheduleDelay);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Thread.currentThread().interrupted();
                            }
                        }

                    }
                });
                thread.start();
                info.setRunThread(thread);
            }
        }
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
