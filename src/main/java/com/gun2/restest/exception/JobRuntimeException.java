package com.gun2.restest.exception;

import com.gun2.restest.component.scheduler.SchedulerInfo;
import lombok.Getter;

/**
 * <b>등록된 {@link com.gun2.restest.entity.Job}을 수행하던 도중 예외 발생</b>
 */
@Getter
public class JobRuntimeException extends RuntimeException {
    private SchedulerInfo schedulerInfo;
    public JobRuntimeException(String message, SchedulerInfo schedulerInfo) {
        super(message);
        this.schedulerInfo = schedulerInfo;
    }

}
