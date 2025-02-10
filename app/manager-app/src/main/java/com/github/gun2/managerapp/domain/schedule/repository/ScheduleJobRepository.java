package com.github.gun2.managerapp.domain.schedule.repository;

import com.github.gun2.managerapp.domain.schedule.repository.custom.ScheduleJobCustomRepository;
import com.github.gun2.managerapp.domain.schedule.entity.ScheduleJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleJobRepository extends JpaRepository<ScheduleJob, Long>, ScheduleJobCustomRepository {
}