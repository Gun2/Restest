package com.gun2.restest.component.scheduler;

import com.gun2.restest.dto.HttpResponseDto;
import com.gun2.restest.dto.SchedulerLogDto;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class SchedulerLogBlockingQueue {
    @Getter
    private BlockingQueue<SchedulerLogDto> schedulerSuccessLogDtoBlockingQueue = new LinkedBlockingQueue<>();
    @Getter
    private BlockingQueue<SchedulerLogDto> schedulerFailureLogDtoBlockingQueue = new LinkedBlockingQueue<>();

    public void addSuccess(SchedulerInfo schedulerInfo, HttpResponseDto httpResponseDto){
        schedulerSuccessLogDtoBlockingQueue.add(new SchedulerLogDto(schedulerInfo,httpResponseDto));
    }
    public void addFailure(SchedulerInfo schedulerInfo, HttpResponseDto httpResponseDto){
        schedulerFailureLogDtoBlockingQueue.add(new SchedulerLogDto(schedulerInfo,httpResponseDto));
    }
}
