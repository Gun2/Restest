package com.github.gun2.managerapp.event;

import com.github.gun2.managerapp.component.scheduler.SchedulerLogBlockingQueue;
import com.github.gun2.managerapp.controller.rest.SchedulerRestController;
import com.github.gun2.managerapp.controller.rest.SysInfoRestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulerEventListener {
    private final SchedulerRestController schedulerRestController;
    private final SchedulerLogBlockingQueue schedulerLogBlockingQueue;
    private final SysInfoRestController sysInfoRestController;

    @EventListener(SchedulerDeleteEvent.class)
    void schedulerDeleteEventListener(SchedulerDeleteEvent event) {
        schedulerRestController.deleteScheduleInfo(event.getSchedulerInfo().getScheduleDto().getId());
    }

    @EventListener(SchedulerRunEvent.class)
    void schedulerRunEventListener(SchedulerRunEvent event) {
        schedulerRestController.insertScheduleInfo(event.getSchedulerInfo());
    }

    @EventListener(SchedulerReceiveSucceedResponseEvent.class)
    void schedulerReceiveSucceedResponseEventListener(SchedulerReceiveSucceedResponseEvent event) {
        schedulerLogBlockingQueue.addSuccess(event.getSchedulerInfo(), event.getHttpResponseDto());
        sysInfoRestController.increaseSuccessNumber();
        schedulerRestController.updateScheduleInfo(event.getSchedulerInfo());
    }

    @EventListener(SchedulerReceiveFailedResponseEvent.class)
    void schedulerReceiveFailedResponseEventListener(SchedulerReceiveFailedResponseEvent event) {
        schedulerLogBlockingQueue.addFailure(event.getSchedulerInfo(), event.getHttpResponseDto());
        sysInfoRestController.increaseFailureNumber();
        schedulerRestController.updateScheduleInfo(event.getSchedulerInfo());
    }
}
