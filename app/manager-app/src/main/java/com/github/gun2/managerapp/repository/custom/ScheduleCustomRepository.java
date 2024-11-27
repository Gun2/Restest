package com.github.gun2.managerapp.repository.custom;

import com.github.gun2.managerapp.dto.ScheduleRunDto;

public interface ScheduleCustomRepository {

    /**
     * <b>스케줄러 run flag 업데이트</b>
     * @param scheduleRunDto 업데이트 정보
     */
    void updateRun(ScheduleRunDto scheduleRunDto);
}
