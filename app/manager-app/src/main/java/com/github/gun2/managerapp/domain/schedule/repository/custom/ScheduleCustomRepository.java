package com.github.gun2.managerapp.domain.schedule.repository.custom;

import com.github.gun2.managerapp.domain.scheduler.dto.ScheduleRunDto;

public interface ScheduleCustomRepository {

    /**
     * <b>스케줄러 run flag 업데이트</b>
     * @param scheduleRunDto 업데이트 정보
     */
    void updateRun(ScheduleRunDto scheduleRunDto);
}
