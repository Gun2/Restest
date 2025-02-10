package com.github.gun2.managerapp.domain.scheduler.component;

import com.github.gun2.managerapp.domain.scheduler.dto.HttpResponseDto;
import com.github.gun2.managerapp.domain.schedule.dto.ScheduleDto;
import com.github.gun2.managerapp.util.LimitedQueue;
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
    //마지막 응답시간
    private long lastTime = 0;
    //동작 스레드
    private Thread runThread = new Thread();
    private LimitedQueue<HttpResponseDto> successResponseList = new LimitedQueue<>(100);
    private LimitedQueue<HttpResponseDto> failureResponseList = new LimitedQueue<>(100);


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

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    /**
     * <p>성공 기록 저장</p>
     * @param httpResponseDto
     */
    public void recordSucceedResponse(HttpResponseDto httpResponseDto){
        this.successResponseList.push(httpResponseDto);
    }

    /**
     * <p>살패 기록 저장</p>
     * @param httpResponseDto
     */
    public void recordFailureResponse(HttpResponseDto httpResponseDto){
        this.failureResponseList.push(httpResponseDto);
    }
}

