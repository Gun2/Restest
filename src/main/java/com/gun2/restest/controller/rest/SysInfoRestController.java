package com.gun2.restest.controller.rest;

import com.gun2.restest.component.AccessUserComponent;
import com.gun2.restest.component.FailureCountComponent;
import com.gun2.restest.component.SuccessCountComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.broker.SubscriptionRegistry;
import org.springframework.messaging.simp.user.SimpSession;
import org.springframework.messaging.simp.user.SimpSubscription;
import org.springframework.messaging.simp.user.SimpSubscriptionMatcher;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@MessageMapping("/sys-info")
@RequestMapping("/sys-info")
public class SysInfoRestController {
    private final AccessUserComponent accessUserComponent;
    private final FailureCountComponent failureCountComponent;
    private final SuccessCountComponent successCountComponent;
    private final SimpMessageSendingOperations sendingOperations;
    private final SimpUserRegistry simpUserRegistry;



    @MessageMapping("/user/init")
    public void userNumber(String msg) {
        sendingOperations.convertAndSend("/sys-info/user", accessUserComponent.getNumber());
    }

    @MessageMapping("/success/init")
    public void successNumber(String msg) {
        sendingOperations.convertAndSend("/sys-info/success", successCountComponent.getNumber());
    }

    @MessageMapping("/failure/init")
    public void failureNumber(String msg) {
        sendingOperations.convertAndSend("/sys-info/failure", failureCountComponent.getNumber());
    }



}
