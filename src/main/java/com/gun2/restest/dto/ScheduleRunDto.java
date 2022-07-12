package com.gun2.restest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * <b>Schedule run flag DTO</b>
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ScheduleRunDto {
    @NotNull
    private Long id;
    @NotNull
    private Boolean run;
}
