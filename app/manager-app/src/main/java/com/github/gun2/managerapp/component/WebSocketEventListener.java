package com.github.gun2.managerapp.component;

import com.github.gun2.managerapp.domain.systemInformation.component.AccessUserComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * webscoket 연결 이벤트 리스너
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketEventListener {
    private final AccessUserComponent accessUserComponent;

    @EventListener(SessionConnectEvent.class)
    private void handleSessionConnected(SessionConnectEvent event) {
        log.info("connect");
        accessUserComponent.increaseNumber();

    }

    @EventListener(SessionDisconnectEvent.class)
    private void handleSessionDisconnect(SessionDisconnectEvent event) {
        log.info("disconnect");
        log.info(event.getSessionId());
        accessUserComponent.decreaseNumber();
    }
}
