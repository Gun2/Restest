package com.github.gun2.managerapp.controller.rest;

import com.github.gun2.managerapp.component.AccessUserComponent;
import com.github.gun2.managerapp.component.FailureCountComponent;
import com.github.gun2.managerapp.component.SuccessCountComponent;
import com.github.gun2.managerapp.constant.SuccessCode;
import com.github.gun2.managerapp.form.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.messaging.SubProtocolWebSocketHandler;

@Slf4j
@RestController
@RequiredArgsConstructor
@MessageMapping("/sys-info")
@RequestMapping("/api")
public class SysInfoRestController {
    private final AccessUserComponent accessUserComponent;
    private final FailureCountComponent failureCountComponent;
    private final SuccessCountComponent successCountComponent;
    private final SimpMessageSendingOperations sendingOperations;
    private final WebSocketHandler webSocketHandler;

    /**
     * 현재 세션 연결 수 반환
     * @return
     */
    @GetMapping("/v1/system-information/users")
    public ResponseEntity<SuccessResponse<Integer>> getUserNumber(){
        int sessionCnt = ((SubProtocolWebSocketHandler)webSocketHandler).getStats().getWebSocketSessions();
        return new SuccessResponse(sessionCnt).toResponseEntity(SuccessCode.OK);
    }

    @MessageMapping("/user/init")
    public void userNumber(String msg) {
        int sessionCnt = ((SubProtocolWebSocketHandler)webSocketHandler).getStats().getWebSocketSessions();
        sendingOperations.convertAndSend("/sys-info/user", sessionCnt);
    }

    /**
     * 총 응답 성공 수 반환
     * @return
     */
    @GetMapping("/v1/system-information/success-count")
    public ResponseEntity<SuccessResponse<Integer>> getSuccessCount(){
        return new SuccessResponse(successCountComponent.getNumber()).toResponseEntity(SuccessCode.OK);
    }
    @MessageMapping("/success/init")
    public void successNumber(String msg) {
        sendingOperations.convertAndSend("/sys-info/success", successCountComponent.getNumber());
    }

    /**
     * 총 응답 실패 수 반환
     * @return
     */
    @GetMapping("/v1/system-information/failure-count")
    public ResponseEntity<SuccessResponse<Integer>> getFailureNumber(){
        return new SuccessResponse(failureCountComponent.getNumber()).toResponseEntity(SuccessCode.OK);
    }
    @MessageMapping("/failure/init")
    public void failureNumber(String msg) {
        sendingOperations.convertAndSend("/sys-info/failure", failureCountComponent.getNumber());
    }

    /**
     * <b>성공 카운터를 증가시키고 전파</b>
     */
    public void increaseSuccessNumber(){
        successCountComponent.increaseNumber();
        this.successNumber("");
    }

    /**
     * <b>실패 카운터를 증가시키고 전파</b>
     */
    public void increaseFailureNumber(){
        failureCountComponent.increaseNumber();
        this.failureNumber("");
    }
}
