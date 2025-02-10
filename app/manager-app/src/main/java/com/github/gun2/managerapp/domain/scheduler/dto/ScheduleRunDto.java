package com.github.gun2.managerapp.domain.scheduler.dto;

import com.github.gun2.managerapp.domain.schedule.dto.ScheduleRunRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * <b>Schedule run flag DTO</b>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ScheduleRunDto {
    private Long id;
    private Boolean run;

    public ScheduleRunDto(ScheduleRunRequest scheduleRunRequest) {
        this.id = scheduleRunRequest.getId();
        this.run = scheduleRunRequest.getRun();
    }
}
