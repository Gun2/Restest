package com.github.gun2.managerapp.domain.schedule.repository.custom.impl;

import com.github.gun2.managerapp.domain.schedule.repository.custom.ScheduleCustomRepository;
import com.github.gun2.managerapp.domain.scheduler.dto.ScheduleRunDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.github.gun2.managerapp.domain.schedule.entity.QSchedule.schedule;

@RequiredArgsConstructor
public class ScheduleCustomRepositoryImpl implements ScheduleCustomRepository {
    private final JPAQueryFactory queryFactory;


    @Override
    public void updateRun(ScheduleRunDto scheduleRunDto) {
        queryFactory.update(schedule)
                .set(schedule.run, scheduleRunDto.getRun())
                .where(schedule.id.eq(scheduleRunDto.getId()))
                .execute();
    }
}
