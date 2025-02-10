package com.github.gun2.managerapp.event;

import com.github.gun2.managerapp.component.scheduler.SchedulerInfo;
import lombok.Getter;
import lombok.ToString;

/**
 * 스케줄러 동작 시 발생되는 이벤트
 */

@ToString
@Getter
public class SchedulerRunEvent {
    private final SchedulerInfo schedulerInfo;

    public SchedulerRunEvent(SchedulerInfo schedulerInfo) {
        this.schedulerInfo = schedulerInfo;
    }
}
