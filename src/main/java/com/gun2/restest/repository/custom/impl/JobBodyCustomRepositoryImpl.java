package com.gun2.restest.repository.custom.impl;

import com.gun2.restest.repository.custom.JobBodyCustomRepository;
import com.gun2.restest.repository.custom.JobHeaderCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import static com.gun2.restest.entity.QJobBody.jobBody;

@RequiredArgsConstructor
public class JobBodyCustomRepositoryImpl implements JobBodyCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public void deleteAllByJobId(@NonNull Long jobId) {
        queryFactory.delete(jobBody).where(jobBody.jobId.eq(jobId)).execute();
    }
}
