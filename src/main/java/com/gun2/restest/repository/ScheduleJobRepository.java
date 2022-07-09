package com.gun2.restest.repository;

import com.gun2.restest.entity.ScheduleJob;
import com.gun2.restest.repository.custom.ScheduleJobCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleJobRepository extends JpaRepository<ScheduleJob, Long>, ScheduleJobCustomRepository {
}