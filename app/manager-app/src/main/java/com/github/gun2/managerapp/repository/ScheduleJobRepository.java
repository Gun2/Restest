package com.github.gun2.managerapp.repository;

import com.github.gun2.managerapp.repository.custom.ScheduleJobCustomRepository;
import com.github.gun2.managerapp.entity.ScheduleJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleJobRepository extends JpaRepository<ScheduleJob, Long>, ScheduleJobCustomRepository {
}