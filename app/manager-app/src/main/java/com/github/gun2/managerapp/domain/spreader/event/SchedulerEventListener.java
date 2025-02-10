package com.github.gun2.managerapp.domain.spreader.event;

import com.github.gun2.managerapp.domain.scheduler.component.SchedulerLogBlockingQueue;
import com.github.gun2.managerapp.domain.scheduler.event.SchedulerDeleteEvent;
import com.github.gun2.managerapp.domain.scheduler.event.SchedulerReceiveFailedResponseEvent;
import com.github.gun2.managerapp.domain.scheduler.event.SchedulerReceiveSucceedResponseEvent;
import com.github.gun2.managerapp.domain.scheduler.event.SchedulerRunEvent;
import com.github.gun2.managerapp.domain.spreader.SchedulerDataSpreader;
import com.github.gun2.managerapp.domain.spreader.SysInfoDataSpreader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 스케줄러의 변경사항을 전파하는 리스너
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulerEventListener {
    private final SchedulerDataSpreader schedulerDataSpreader;
    private final SchedulerLogBlockingQueue schedulerLogBlockingQueue;
    private final SysInfoDataSpreader sysInfoDataSpreader;

    @EventListener(SchedulerDeleteEvent.class)
    void schedulerDeleteEventListener(SchedulerDeleteEvent event) {
        schedulerDataSpreader.deleteScheduleInfo(event.getSchedulerInfo().getScheduleDto().getId());
    }

    @EventListener(SchedulerRunEvent.class)
    void schedulerRunEventListener(SchedulerRunEvent event) {
        schedulerDataSpreader.insertScheduleInfo(event.getSchedulerInfo());
    }

    @EventListener(SchedulerReceiveSucceedResponseEvent.class)
    void schedulerReceiveSucceedResponseEventListener(SchedulerReceiveSucceedResponseEvent event) {
        schedulerLogBlockingQueue.addSuccess(event.getSchedulerInfo(), event.getHttpResponseDto());
        sysInfoDataSpreader.increaseAndSpreadSuccessCount();
        schedulerDataSpreader.updateScheduleInfo(event.getSchedulerInfo());
    }

    @EventListener(SchedulerReceiveFailedResponseEvent.class)
    void schedulerReceiveFailedResponseEventListener(SchedulerReceiveFailedResponseEvent event) {
        schedulerLogBlockingQueue.addFailure(event.getSchedulerInfo(), event.getHttpResponseDto());
        sysInfoDataSpreader.increaseAndSpreadFailureCount();
        schedulerDataSpreader.updateScheduleInfo(event.getSchedulerInfo());
    }
}
