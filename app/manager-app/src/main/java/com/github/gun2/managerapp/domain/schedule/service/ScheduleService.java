package com.github.gun2.managerapp.domain.schedule.service;

import com.github.gun2.managerapp.domain.schedule.dto.ScheduleDto;
import com.github.gun2.managerapp.domain.scheduler.dto.ScheduleRunDto;

import java.util.List;

public interface ScheduleService {
    List<ScheduleDto> findAll();

    ScheduleDto findById(Long id);

    ScheduleDto insert(ScheduleDto scheduleDto);

    ScheduleDto update(ScheduleDto scheduleDto);

    void delete(long id);

    void updateRun(ScheduleRunDto scheduleRunDto);

    List<ScheduleDto> findByRun(boolean run);
}
