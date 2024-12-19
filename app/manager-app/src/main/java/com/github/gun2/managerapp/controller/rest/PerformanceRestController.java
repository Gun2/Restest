package com.github.gun2.managerapp.controller.rest;

import com.github.gun2.managerapp.constant.SuccessCode;
import com.github.gun2.managerapp.component.performance.PerformanceData;
import com.github.gun2.managerapp.dto.PerformanceDto;
import com.github.gun2.managerapp.form.request.PerformanceCreateRequest;
import com.github.gun2.managerapp.form.request.PerformanceRequest;
import com.github.gun2.managerapp.form.response.SuccessResponse;
import com.github.gun2.managerapp.service.PerformanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@MessageMapping("/performance")
@RequestMapping("/api")
public class PerformanceRestController {

    private final SimpMessageSendingOperations sendingOperations;
    private final PerformanceService performanceService;

    @GetMapping("/v1/performances/max-instance")
    public ResponseEntity<SuccessResponse<Integer>> getMaxInstance(){
        int maxInstance = performanceService.getMaxInstance();
        return SuccessResponse.of(maxInstance).toResponseEntity(SuccessCode.OK);
    }

    @GetMapping("/v1/performances/max-job")
    public ResponseEntity<SuccessResponse<Integer>> getMaxJob(){
        int maxJob = performanceService.getMaxJob();
        return SuccessResponse.of(maxJob).toResponseEntity(SuccessCode.OK);
    }

    @PostMapping("/v1/performances")
    public ResponseEntity<SuccessResponse<PerformanceDto>> create(@RequestBody @Validated PerformanceCreateRequest performanceCreateRequest, BindingResult bindingResult) throws BindException{
        performanceService.validate(performanceCreateRequest, bindingResult);
        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }
        PerformanceDto performanceDto = performanceService.create(performanceCreateRequest);
        return SuccessResponse.of(performanceDto).toResponseEntity(SuccessCode.CREATED);
    }

    @PostMapping("/v1/performances/start")
    public ResponseEntity<SuccessResponse<String>> start(@RequestBody @Validated PerformanceRequest performanceRequest){
        performanceService.start(performanceRequest.getUuid());
        return SuccessResponse.of(performanceRequest.getUuid()).toResponseEntity(SuccessCode.OK);
    }

    @PostMapping("/v1/performances/stop")
    public ResponseEntity<SuccessResponse<String>> stop(@RequestBody @Validated PerformanceRequest performanceRequest){
        performanceService.stop(performanceRequest.getUuid());
        return SuccessResponse.of(performanceRequest.getUuid()).toResponseEntity(SuccessCode.OK);
    }

    public void pushPerformanceData(PerformanceData performanceData){
        sendingOperations.convertAndSend("/performance/"+performanceData.getUuid(), performanceData);
    }

    /**
     * <p>성능측정 종료 플래스 사용자에게 전달</p>
     * @param uuid
     */
    public void pushPerformanceStop(String uuid){
        sendingOperations.convertAndSend("/performance/"+uuid+"/stop", "stop");
    }
}
