package com.github.gun2.managerapp.domain.systemInformation.controller;

import com.github.gun2.managerapp.constant.SuccessCode;
import com.github.gun2.managerapp.domain.systemInformation.component.FailureCountComponent;
import com.github.gun2.managerapp.domain.systemInformation.component.SuccessCountComponent;
import com.github.gun2.managerapp.form.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
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
    private final FailureCountComponent failureCountComponent;
    private final SuccessCountComponent successCountComponent;
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

    /**
     * 총 응답 성공 수 반환
     * @return
     */
    @GetMapping("/v1/system-information/success-count")
    public ResponseEntity<SuccessResponse<Integer>> getSuccessCount(){
        return new SuccessResponse(successCountComponent.getNumber()).toResponseEntity(SuccessCode.OK);
    }

    /**
     * 총 응답 실패 수 반환
     * @return
     */
    @GetMapping("/v1/system-information/failure-count")
    public ResponseEntity<SuccessResponse<Integer>> getFailureNumber(){
        return new SuccessResponse(failureCountComponent.getNumber()).toResponseEntity(SuccessCode.OK);
    }

}
