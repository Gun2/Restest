package com.github.gun2.managerapp.domain.scheduler.service;

import com.github.gun2.managerapp.domain.scheduler.component.SchedulerInfo;
import com.github.gun2.managerapp.domain.scheduler.dto.SchedulerStateDto;

import java.util.List;

public interface SchedulerService {
    List<SchedulerStateDto> findAll();

    SchedulerInfo findByScheduleId(Long id);
}
