package com.github.gun2.managerapp.controller.rest;

import com.github.gun2.managerapp.component.scheduler.SchedulerInfo;
import com.github.gun2.managerapp.constant.SuccessCode;
import com.github.gun2.managerapp.dto.HttpResponseDto;
import com.github.gun2.managerapp.dto.SchedulerStateDto;
import com.github.gun2.managerapp.form.response.SuccessResponse;
import com.github.gun2.managerapp.service.SchedulerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@MessageMapping("/scheduler")
@RequestMapping("/api")
public class SchedulerRestController {
    //TODO: 정보 전파 로직을 별도의 클래스로 이동
    private final SimpMessageSendingOperations sendingOperations;
    private final SchedulerService schedulerService;

    public SchedulerRestController(SimpMessageSendingOperations sendingOperations, SchedulerService schedulerService) {
        this.sendingOperations = sendingOperations;
        this.schedulerService = schedulerService;
    }

    @GetMapping("/v1/schedulers")
    public ResponseEntity<SuccessResponse<List<SchedulerStateDto>>> findAll(){
        List<SchedulerStateDto> schedulerStateDtoList =  schedulerService.findAll();
        return SuccessResponse.of(schedulerStateDtoList).toResponseEntity(SuccessCode.OK);
    }

    public void deleteScheduleInfo(Long id){
        sendingOperations.convertAndSend("/scheduler/delete", id);
    }

    public void insertScheduleInfo(SchedulerInfo schedulerInfo){
        sendingOperations.convertAndSend("/scheduler/insert", SchedulerStateDto.of(schedulerInfo));
    }

    public void updateScheduleInfo(SchedulerInfo schedulerInfo){
        sendingOperations.convertAndSend("/scheduler/update", SchedulerStateDto.of(schedulerInfo));
    }

    public void pushFailedResponse(Long scheduleId, HttpResponseDto httpResponseDto){
        sendingOperations.convertAndSend("/scheduler/"+scheduleId+"/failure", httpResponseDto);
    }
    public void pushSucceedResponse(Long scheduleId, HttpResponseDto httpResponseDto){
        sendingOperations.convertAndSend("/scheduler/"+scheduleId+"/success", httpResponseDto);
    }


    /**
     * <p>스케줄id에 해당하는 스케줄러가 실패한 내역 조회</p>
     * @param id
     * @return
     */
    @GetMapping("/v1/scheduler/{id}/responses/failures")
    @ResponseBody
    public ResponseEntity<List<HttpResponseDto>> findFailedResponses(@PathVariable("id") Long id){
        SchedulerInfo schedulerInfo = schedulerService.findByScheduleId(id);
        return SuccessResponse.of(schedulerInfo.getFailureResponseList().toList()).toResponseEntity(SuccessCode.OK);
    }

    /**
     * <p>스케줄id에 해당하는 스케줄러가 성공한 내역 조회</p>
     * @param id
     * @return
     */
    @GetMapping("/v1/scheduler/{id}/responses/successes")
    @ResponseBody
    public ResponseEntity<List<HttpResponseDto>> findSucceedResponses(@PathVariable("id") Long id){
        SchedulerInfo schedulerInfo = schedulerService.findByScheduleId(id);
        return SuccessResponse.of(schedulerInfo.getSuccessResponseList().toList()).toResponseEntity(SuccessCode.OK);
    }

}
