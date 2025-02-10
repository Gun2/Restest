package com.github.gun2.managerapp.event;

import com.github.gun2.managerapp.component.scheduler.SchedulerInfo;
import com.github.gun2.managerapp.dto.HttpResponseDto;
import lombok.Getter;
import lombok.ToString;

/**
 * 스케줄러가 성공 응답을 받을 경우 발생하는 이벤트
 */
@Getter
@ToString
public class SchedulerReceiveSucceedResponseEvent {
    //응답값
    private final HttpResponseDto httpResponseDto;
    //동작한 스케줄러 정보
    private final SchedulerInfo schedulerInfo;

    public SchedulerReceiveSucceedResponseEvent(HttpResponseDto httpResponseDto, SchedulerInfo schedulerInfo) {
        this.httpResponseDto = httpResponseDto;
        this.schedulerInfo = schedulerInfo;
    }
}
