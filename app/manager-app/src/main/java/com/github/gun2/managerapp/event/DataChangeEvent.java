package com.github.gun2.managerapp.event;

import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.UUID;

/**
 * 도메인 데이터 변경 시 발생하는 이벤트
 * @param <T> 타입
 */
@Getter
@ToString
public class DataChangeEvent<T> {
    //고유값
    private final UUID uuid;
    //생성시간
    private final Instant createdAt;
    //데이터
    private final T data;
    //데이터 변경 유형
    private final Type type;
    public enum Type {
        CREATE,
        UPDATE,
        DELETE;
    }

    public DataChangeEvent(T data, Type type) {
        this.data = data;
        this.type = type;
        this.uuid = UUID.randomUUID();
        this.createdAt = Instant.now();
    }
}
