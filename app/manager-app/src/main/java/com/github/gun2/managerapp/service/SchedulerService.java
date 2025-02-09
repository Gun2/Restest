package com.github.gun2.managerapp.service;

import com.github.gun2.managerapp.component.scheduler.SchedulerInfo;
import com.github.gun2.managerapp.dto.SchedulerStateDto;

import java.util.List;

public interface SchedulerService {
    List<SchedulerStateDto> findAll();

    SchedulerInfo findByScheduleId(Long id);
}
