package com.gun2.restest.repository.custom.impl;

import com.gun2.restest.repository.custom.JobHeaderCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import static com.gun2.restest.entity.QJobHeader.jobHeader;

@RequiredArgsConstructor
public class JobHeaderCustomRepositoryImpl implements JobHeaderCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public void deleteAllByJobId(@NonNull Long jobId) {
        queryFactory.delete(jobHeader).where(jobHeader.job.id.eq(jobId)).execute();
    }
}
