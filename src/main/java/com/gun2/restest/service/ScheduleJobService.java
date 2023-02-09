package com.gun2.restest.service;

import com.gun2.restest.dto.ScheduleJobDto;
import com.gun2.restest.entity.Schedule;

import java.util.List;

public interface ScheduleJobService {

    List<ScheduleJobDto> insertAll(Schedule schedule, List<Long> jobIdList);

    void deleteByScheduleId(Long scheduleId);
}
