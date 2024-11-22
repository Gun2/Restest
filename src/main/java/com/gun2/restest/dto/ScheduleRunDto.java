package com.gun2.restest.dto;

import com.gun2.restest.form.request.ScheduleRunRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.validation.constraints.NotNull;

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
