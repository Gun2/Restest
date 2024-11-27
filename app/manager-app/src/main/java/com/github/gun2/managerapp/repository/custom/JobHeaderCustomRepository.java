package com.github.gun2.managerapp.repository.custom;

public interface JobHeaderCustomRepository {

    /**
     * <b>업무 id가 같은 데이터 모두 삭제</b>
     * @param jobId 스케줄러 id
     */
    void deleteAllByJobId(Long jobId);
}
