package com.github.gun2.managerapp.domain.schedule.event;

import com.github.gun2.managerapp.domain.schedule.dto.ScheduleDto;
import com.github.gun2.managerapp.event.DataChangeEvent;

/**
 * schedule 도메인 변경 시 발생하는 이벤트
 */
public class ScheduleDataChangeEvent extends DataChangeEvent<ScheduleDto> {

    public ScheduleDataChangeEvent(ScheduleDto data, Type type) {
        super(data, type);
    }
}
