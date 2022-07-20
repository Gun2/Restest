package com.gun2.restest.component;

import com.gun2.restest.component.scheduler.SchedulerComponent;
import com.gun2.restest.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <b>앱 동작 관련 컴포넌트</b>
 */
@Component
@Slf4j
public class AppComponent {
    private final SchedulerComponent schedulerComponent;
    private final ScheduleService scheduleService;

    public AppComponent(SchedulerComponent schedulerComponent, ScheduleService scheduleService) {
        this.schedulerComponent = schedulerComponent;
        this.scheduleService = scheduleService;

        this.init();
    }

    private void init(){
        scheduleInit();
    }

    private void scheduleInit(){
        schedulerComponent.init(scheduleService.findByRun(true));
        schedulerComponent.run();
    }}
