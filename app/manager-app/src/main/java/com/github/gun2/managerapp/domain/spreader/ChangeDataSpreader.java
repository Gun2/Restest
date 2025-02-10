package com.github.gun2.managerapp.domain.spreader;

import com.github.gun2.managerapp.domain.spreader.dto.ChangeDataDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

/**
 * <p>데이터의 변경사항을 클라이언트에게 전달하기 위한 추상 클래스</p>
 * @param <T> DTO객체 타입
 * @param <K> DTO객체의 key에 해당하는 필드의 타입
 */
@RequiredArgsConstructor
public abstract class ChangeDataSpreader<T, K> {
    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final String TOPIC;
    public void create(T data){
        simpMessageSendingOperations.convertAndSend(TOPIC, ChangeDataDto.ofCreatedData(data));
    }
    public void update(T data){
        simpMessageSendingOperations.convertAndSend(TOPIC, ChangeDataDto.ofUpdatedData(data));
    }
    public void delete(K key){
        simpMessageSendingOperations.convertAndSend(TOPIC, ChangeDataDto.ofDeletedData(key));
    }
}
