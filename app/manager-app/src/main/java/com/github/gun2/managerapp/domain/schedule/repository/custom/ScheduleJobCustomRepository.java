package com.github.gun2.managerapp.domain.schedule.repository.custom;

public interface ScheduleJobCustomRepository {

    /**
     * <b>스케줄러 id가 같은 데이터 모두 삭제</b>
     * @param scheduleId 스케줄러 id
     */
    void deleteAllByScheduleId(Long scheduleId);

    /**
     * <b>업무 id가 같은 데이터 모두 삭제</b>
     * @param jobId 스케줄러 id
     */
    void deleteAllByJobId(Long jobId);
}
