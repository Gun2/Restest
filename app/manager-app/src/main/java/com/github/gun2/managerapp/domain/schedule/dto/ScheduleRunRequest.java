package com.github.gun2.managerapp.domain.schedule.dto;

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
public class ScheduleRunRequest {
    @NotNull
    private Long id;
    @NotNull
    private Boolean run;
}
