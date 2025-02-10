package com.github.gun2.managerapp.domain.scheduler.event;

import com.github.gun2.managerapp.domain.scheduler.component.SchedulerInfo;
import lombok.Getter;

/**
 * 스케줄러 삭제 후 발생되는 이벤트
 */
@Getter
public class SchedulerDeleteEvent {
    //삭제한 스케줄러 정보
    private final SchedulerInfo schedulerInfo;

    public SchedulerDeleteEvent(SchedulerInfo schedulerInfo) {
        this.schedulerInfo = schedulerInfo;
    }
}
