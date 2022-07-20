package com.gun2.restest.service;

import com.gun2.restest.dto.JobDto;
import com.gun2.restest.dto.ScheduleDto;
import com.gun2.restest.dto.ScheduleRunDto;

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
