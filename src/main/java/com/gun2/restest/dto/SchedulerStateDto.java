package com.gun2.restest.dto;

import com.gun2.restest.component.scheduler.SchedulerInfo;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SchedulerStateDto {
    private Long id;
    private int success;
    private int failure;
    private long lastTime;

    public SchedulerStateDto(SchedulerInfo schedulerInfo) {
        this.id = schedulerInfo.getScheduleDto().getId();
        this.success = schedulerInfo.getSuccessCount().get();
        this.failure = schedulerInfo.getFailureCount().get();
        this.lastTime =schedulerInfo.getLastTime();
    }

    public static SchedulerStateDto of(SchedulerInfo schedulerInfo){
        return new SchedulerStateDto(schedulerInfo);
    }
}
