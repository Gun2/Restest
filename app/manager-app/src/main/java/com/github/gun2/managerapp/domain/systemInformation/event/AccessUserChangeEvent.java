package com.github.gun2.managerapp.domain.systemInformation.event;

import lombok.Getter;
import lombok.ToString;

/**
 * 접속한 사용자 카운트 정보 변경 시 발생되는 이벤트
 */
@Getter
@ToString
public class AccessUserChangeEvent {
    //접속중인 사용자 수
    private final int count;

    public AccessUserChangeEvent(int count) {
        this.count = count;
    }
}
