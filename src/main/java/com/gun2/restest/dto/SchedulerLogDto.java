package com.gun2.restest.dto;

import com.gun2.restest.component.scheduler.SchedulerInfo;
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
