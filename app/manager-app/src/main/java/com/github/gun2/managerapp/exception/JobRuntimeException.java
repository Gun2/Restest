package com.github.gun2.managerapp.exception;

import com.github.gun2.managerapp.component.scheduler.SchedulerInfo;
import com.github.gun2.managerapp.entity.Job;
import lombok.Getter;

/**
 * <b>등록된 {@link Job}을 수행하던 도중 예외 발생</b>
 */
@Getter
public class JobRuntimeException extends RuntimeException {
    private SchedulerInfo schedulerInfo;
    public JobRuntimeException(String message, SchedulerInfo schedulerInfo) {
        super(message);
        this.schedulerInfo = schedulerInfo;
    }

}
