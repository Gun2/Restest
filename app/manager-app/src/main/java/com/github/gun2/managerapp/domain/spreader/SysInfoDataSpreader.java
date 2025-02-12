package com.github.gun2.managerapp.domain.spreader;

import com.github.gun2.managerapp.domain.systemInformation.component.FailureCountComponent;
import com.github.gun2.managerapp.domain.systemInformation.component.SuccessCountComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.messaging.SubProtocolWebSocketHandler;

/**
 * 시스템 정보를 websocket을 통해 전파하는 클래스
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class SysInfoDataSpreader {
    private final SimpMessageSendingOperations sendingOperations;
    private final WebSocketHandler webSocketHandler;
    private final FailureCountComponent failureCountComponent;
    private final SuccessCountComponent successCountComponent;

    /**
     * 접속중인 사용자 수 전파
     */
    public void spreadAccessUserCount() {
        int sessionCnt = ((SubProtocolWebSocketHandler)webSocketHandler).getStats().getWebSocketSessions();
        sendingOperations.convertAndSend("/sys-info/user", sessionCnt);
    }

    /**
     * 요청 성공 카운트 전파
     */
    public void spreadSuccessCount() {
        sendingOperations.convertAndSend("/sys-info/success", successCountComponent.getNumber());
    }

    /**
     * 요청 실패 카운트 전파
     */
    public void spreadFailureCount() {
        sendingOperations.convertAndSend("/sys-info/failure", failureCountComponent.getNumber());
    }

    /**
     * <b>성공 카운터를 증가시키고 전파</b>
     */
    public void increaseAndSpreadSuccessCount(){
        successCountComponent.increaseNumber();
        this.spreadSuccessCount();
    }

    /**
     * <b>실패 카운터를 증가시키고 전파</b>
     */
    public void increaseAndSpreadFailureCount(){
        failureCountComponent.increaseNumber();
        this.spreadFailureCount();
    }
}
