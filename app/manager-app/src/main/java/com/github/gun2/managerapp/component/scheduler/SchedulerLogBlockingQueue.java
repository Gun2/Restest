package com.github.gun2.managerapp.component.scheduler;

import com.github.gun2.managerapp.dto.HttpResponseDto;
import com.github.gun2.managerapp.dto.SchedulerLogDto;
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
