package com.gun2.restest.repository.custom.impl;

import com.gun2.restest.dto.ScheduleRunDto;
import com.gun2.restest.repository.custom.ScheduleCustomRepository;
import com.gun2.restest.repository.custom.ScheduleJobCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import static com.gun2.restest.entity.QSchedule.schedule;

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
