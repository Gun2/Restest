package com.github.gun2.managerapp.service;

import com.github.gun2.managerapp.dto.ScheduleJobDto;
import com.github.gun2.managerapp.entity.Schedule;

import java.util.List;

public interface ScheduleJobService {

    List<ScheduleJobDto> insertAll(Schedule schedule, List<Long> jobIdList);

    void deleteByScheduleId(Long scheduleId);
}
