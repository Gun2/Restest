package com.github.gun2.managerapp.domain.scheduler.controller;

import com.github.gun2.managerapp.constant.SuccessCode;
import com.github.gun2.managerapp.domain.scheduler.component.SchedulerInfo;
import com.github.gun2.managerapp.domain.scheduler.dto.HttpResponseDto;
import com.github.gun2.managerapp.domain.scheduler.dto.SchedulerStateDto;
import com.github.gun2.managerapp.domain.scheduler.service.SchedulerService;
import com.github.gun2.managerapp.form.response.SuccessResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@MessageMapping("/scheduler")
@RequestMapping("/api")
public class SchedulerRestController {
    private final SchedulerService schedulerService;

    public SchedulerRestController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    @GetMapping("/v1/schedulers")
    public ResponseEntity<SuccessResponse<List<SchedulerStateDto>>> findAll(){
        List<SchedulerStateDto> schedulerStateDtoList =  schedulerService.findAll();
        return SuccessResponse.of(schedulerStateDtoList).toResponseEntity(SuccessCode.OK);
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
