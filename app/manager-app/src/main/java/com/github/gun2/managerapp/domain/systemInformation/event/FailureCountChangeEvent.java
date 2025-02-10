package com.github.gun2.managerapp.domain.systemInformation.event;

import lombok.Getter;
import lombok.ToString;

/**
 * 요청 성공 카운트 정보 변경 시 발생되는 이벤트
 */
@Getter
@ToString
public class FailureCountChangeEvent {
    //요청 성공 카운트 수
    private final int count;

    public FailureCountChangeEvent(int count) {
        this.count = count;
    }
}
