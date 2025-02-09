package com.github.gun2.managerapp.service.impl;

import com.github.gun2.managerapp.component.scheduler.SchedulerComponent;
import com.github.gun2.managerapp.component.scheduler.SchedulerInfo;
import com.github.gun2.managerapp.dto.SchedulerStateDto;
import com.github.gun2.managerapp.service.SchedulerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SchedulerServiceImpl implements SchedulerService {
    private final SchedulerComponent schedulerComponent;

    @Override
    public List<SchedulerStateDto> findAll() {
        return schedulerComponent
                .getSchedulerInfoMap()
                .values()
                .stream()
                .map(info -> SchedulerStateDto.of(info)).toList();
    }

    @Override
    public SchedulerInfo findByScheduleId(Long scheduleId) {
        return schedulerComponent.getSchedulerInfoMap().get(scheduleId);
    }
}
