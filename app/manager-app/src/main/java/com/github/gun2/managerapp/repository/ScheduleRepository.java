package com.github.gun2.managerapp.repository;

import com.github.gun2.managerapp.repository.custom.ScheduleCustomRepository;
import com.github.gun2.managerapp.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long>, ScheduleCustomRepository {

    List<Schedule> findByRun(boolean run);
}