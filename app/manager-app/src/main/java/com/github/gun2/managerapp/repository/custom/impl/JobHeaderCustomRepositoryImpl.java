package com.github.gun2.managerapp.repository.custom.impl;

import com.github.gun2.managerapp.repository.custom.JobHeaderCustomRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import static com.github.gun2.managerapp.entity.QJobHeader.jobHeader;

@RequiredArgsConstructor
public class JobHeaderCustomRepositoryImpl implements JobHeaderCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public void deleteAllByJobId(@NonNull Long jobId) {
        queryFactory.delete(jobHeader).where(jobHeader.job.id.eq(jobId)).execute();
    }
}
