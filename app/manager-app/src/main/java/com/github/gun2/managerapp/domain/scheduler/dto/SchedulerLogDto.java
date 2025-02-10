package com.github.gun2.managerapp.domain.scheduler.dto;

import com.github.gun2.managerapp.domain.scheduler.component.SchedulerInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * <p>scheduler 결과 log 정보</p>
 */
@Getter
@RequiredArgsConstructor
public class SchedulerLogDto {
    private final SchedulerInfo schedulerInfo;
    private final HttpResponseDto httpResponseDto;
}
