package com.github.gun2.managerapp.domain.job.repository.custom.impl;

import com.github.gun2.managerapp.domain.job.repository.custom.JobBodyCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import static com.github.gun2.managerapp.domain.job.entity.QJobBody.jobBody;

@RequiredArgsConstructor
public class JobBodyCustomRepositoryImpl implements JobBodyCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public void deleteAllByJobId(@NonNull Long jobId) {
        queryFactory.delete(jobBody).where(jobBody.job.id.eq(jobId)).execute();
    }
}
