package com.gun2.restest.component.scheduler;

import com.gun2.restest.controller.rest.SchedulerRestController;
import com.gun2.restest.controller.rest.SysInfoRestController;
import com.gun2.restest.dto.JobBodyDto;
import com.gun2.restest.dto.ScheduleDto;
import com.gun2.restest.exception.JobRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

    /**
     * <b>스케줄 정보 업데이트</b>
     * @param scheduleDto 업데이트할 스케줄
     */
    public void updateSchedule(ScheduleDto scheduleDto){
        if(schedulerInfoMap.containsKey(scheduleDto.getId())){
            //schedule 정보만 업데이트
            SchedulerInfo schedulerInfo = schedulerInfoMap.get(scheduleDto.getId());
            schedulerInfo.setScheduleDto(scheduleDto);
        }else{
            //새로 만들기
            schedulerInfoMap.put(scheduleDto.getId(), new SchedulerInfo(scheduleDto));
        }
        this.run();
    }

    public void deleteSchedule(Long scheduleId){
        if(schedulerInfoMap.containsKey(scheduleId)){
            schedulerInfoMap.remove(scheduleId);
            schedulerRestController.deleteScheduleInfo(scheduleId);
        }
    }

    /**
     * <b>스케줄러 정보 가져오기</b>
     * @return 스케줄러 정보 map
     */
    public ConcurrentReferenceHashMap<Long, SchedulerInfo> getSchedulerInfoMap() {
        return schedulerInfoMap;
    }

    /**
     * <b>컴폰넌트 초기화</b>
     */
    public void init(List<ScheduleDto> scheduleDtoList){
        //List<ScheduleDto> scheduleDtoList = scheduleService.findByRun(true);
        scheduleDtoList.forEach( scheduleDto -> {
            schedulerInfoMap.put(scheduleDto.getId(), new SchedulerInfo(scheduleDto));
        });
    }

    public void run(){
        log.info("RestSchedulerComponent run!!!");
        for (Long id : schedulerInfoMap.keySet()){
            final SchedulerInfo info = schedulerInfoMap.get(id);
            if(!info.getRunThread().isAlive() || info.getRunThread().isInterrupted()){
                schedulerRestController.insertScheduleInfo(info);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (!Thread.currentThread().isInterrupted()){
                            ScheduleDto scheduleDto = info.getScheduleDto();
                            scheduleDto.getJobList().forEach(job -> {

                                List<JobBodyDto> usableBody = job.getJobBodyList()
                                        .stream()
                                        .filter(body -> body.isUsable())
                                        .toList();
                                usableBody.forEach( body -> {
                                    try {
                                        log.info("[TEST] schedule id : {}, job id : {}, job url : {}, body : {}",
                                                scheduleDto.getId(),
                                                job.getId(),
                                                job.getUrl(),
                                                body.getBody());
                                        info.getSuccessCount().incrementAndGet();
                                        sysInfoRestController.increaseSuccessNumber();
                                        schedulerRestController.updateScheduleInfo(info);
                                    }catch (Exception e){
                                        info.getFailureCount().decrementAndGet();
                                        throw new JobRuntimeException(e.getMessage(), info);
                                    }
                                    try {
                                        Thread.currentThread().sleep(body.getAfterDelay());
                                    }catch (Exception e){
                                        Thread.currentThread().interrupted();
                                    }
                                });
                            });
                            //실행 리스트에 해당 스케줄이 없으면 종료
                            if(!schedulerInfoMap.containsKey(scheduleDto.getId())){
                                break;
                            }
                            try {
                                log.info("schedule id: {}, sleep : {}",
                                        scheduleDto.getId()
                                        , scheduleDto.getDelay());
                                Thread.currentThread().sleep(scheduleDto.getDelay());
                            }catch (Exception e){
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
     * @param schedulerInfo
     */
    public void restartSchedule(SchedulerInfo schedulerInfo){
        log.info("scheduler id : {} restart....", schedulerInfo.getScheduleDto().getId());
        schedulerInfo.getRunThread().interrupt();
        this.run();
    }

    @ExceptionHandler(JobRuntimeException.class)
    private void jobRuntimeExceptionHandler(JobRuntimeException jobRuntimeException){
        sysInfoRestController.increaseFailureNumber();
        jobRuntimeException.getSchedulerInfo().getFailureCount().getAndIncrement();
        log.error("schedule id : {} has errors : {}",
                jobRuntimeException.getSchedulerInfo().getScheduleDto().getId(),
                jobRuntimeException.getMessage());
        //restartSchedule(jobRuntimeException.getSchedulerInfo());
        schedulerRestController.updateScheduleInfo(jobRuntimeException.getSchedulerInfo());

    }
}
