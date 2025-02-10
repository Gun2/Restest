package com.github.gun2.managerapp.domain.schedule.service;

import com.github.gun2.managerapp.domain.schedule.dto.ScheduleJobDto;
import com.github.gun2.managerapp.domain.schedule.entity.Schedule;

import java.util.List;

public interface ScheduleJobService {

    List<ScheduleJobDto> insertAll(Schedule schedule, List<Long> jobIdList);

    void deleteByScheduleId(Long scheduleId);
}
