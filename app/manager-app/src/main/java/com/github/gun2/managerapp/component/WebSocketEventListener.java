package com.github.gun2.managerapp.component;

import com.github.gun2.managerapp.controller.rest.SysInfoRestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketEventListener {
    private final AccessUserComponent accessUserComponent;
    private final SysInfoRestController sysInfoRestController;

    @EventListener(SessionConnectEvent.class)
    private void handleSessionConnected(SessionConnectEvent event) {
        log.info("connect");
        accessUserComponent.increaseNumber();
        sysInfoRestController.userNumber("add");

    }

    @EventListener(SessionDisconnectEvent.class)
    private void handleSessionDisconnect(SessionDisconnectEvent event) {
        log.info("disconnect");
        log.info(event.getSessionId());
        accessUserComponent.decreaseNumber();
        sysInfoRestController.userNumber("remove");
    }
}
