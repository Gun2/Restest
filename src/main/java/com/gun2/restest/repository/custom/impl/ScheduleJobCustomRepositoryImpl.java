package com.gun2.restest.repository.custom.impl;

import com.gun2.restest.repository.custom.ScheduleJobCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.util.Assert;

import static com.gun2.restest.entity.QScheduleJob.scheduleJob;
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
