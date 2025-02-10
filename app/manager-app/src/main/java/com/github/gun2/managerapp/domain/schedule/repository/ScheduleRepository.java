package com.github.gun2.managerapp.domain.schedule.repository;

import com.github.gun2.managerapp.domain.schedule.repository.custom.ScheduleCustomRepository;
import com.github.gun2.managerapp.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleCustomRepository {

    List<Schedule> findByRun(boolean run);
}