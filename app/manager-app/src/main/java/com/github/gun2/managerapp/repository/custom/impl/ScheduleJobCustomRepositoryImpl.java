package com.github.gun2.managerapp.repository.custom.impl;

import com.github.gun2.managerapp.repository.custom.ScheduleJobCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import static com.github.gun2.managerapp.entity.QScheduleJob.scheduleJob;
@RequiredArgsConstructor
public class ScheduleJobCustomRepositoryImpl implements ScheduleJobCustomRepository {
    private final JPAQueryFactory queryFactory;


    @Override
    public void deleteAllByScheduleId(@NonNull Long scheduleId) {
        queryFactory.delete(scheduleJob).where(scheduleJob.schedule.id.eq(scheduleId)).execute();
    }

    @Override
    public void deleteAllByJobId(Long jobId) {
        queryFactory.delete(scheduleJob).where(scheduleJob.job.id.eq(jobId)).execute();
    }
}
