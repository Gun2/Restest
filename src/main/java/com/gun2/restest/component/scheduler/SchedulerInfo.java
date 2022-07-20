package com.gun2.restest.component.scheduler;

import com.gun2.restest.dto.ScheduleDto;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * <b>스케줄러 정보</b>
 */
@Getter
public class SchedulerInfo {
    //스케줄 정보
    private ScheduleDto scheduleDto;
    //성공횟수
    private AtomicInteger successCount = new AtomicInteger();
    //실패횟수
    private AtomicInteger failureCount = new AtomicInteger();
    //동작 스레드
    private Thread runThread = new Thread();

    /**
     * 새로 생성
     * @param scheduleDto 신규 생성할 schedule
     */
    public SchedulerInfo(ScheduleDto scheduleDto){
        this.scheduleDto = scheduleDto;
    }

    public void setScheduleDto(ScheduleDto scheduleDto){
        this.scheduleDto = scheduleDto;
    }

    /**
     * <b>동작 스레드 변경</b>
     * @param runThread 동작시킬 스레드
     */
    public void setRunThread(Thread runThread){
        this.runThread = runThread;
    }
}
