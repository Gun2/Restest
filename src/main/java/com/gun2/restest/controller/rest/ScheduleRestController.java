package com.gun2.restest.controller.rest;

import com.gun2.restest.component.scheduler.SchedulerInfo;
import com.gun2.restest.constant.SuccessCode;
import com.gun2.restest.dto.ScheduleDto;
import com.gun2.restest.dto.ScheduleRunDto;
import com.gun2.restest.form.request.ScheduleRequest;
import com.gun2.restest.form.request.ScheduleRunRequest;
import com.gun2.restest.form.response.SuccessResponse;
import com.gun2.restest.service.ScheduleService;
import com.gun2.restest.spreader.ScheduleChangeDataSpreader;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ScheduleRestController {
    private final ScheduleService scheduleService;
    private final ScheduleChangeDataSpreader scheduleChangeDataSpreader;

    @ApiOperation(value = "모든 스케줄 조회", notes="스케줄 테이블의 모든 값을 반환")
    @GetMapping(path = "/v1/schedules")
    public ResponseEntity<SuccessResponse> findAll(){
        List<ScheduleDto> items = scheduleService.findAll();
        return new SuccessResponse(items).toResponseEntity(SuccessCode.OK);
    }

    @GetMapping(path = "/v1/schedules/{id}")
    public ResponseEntity<SuccessResponse> findById(@PathVariable Long id){
        ScheduleDto scheduleDto = scheduleService.findById(id);
        return new SuccessResponse(scheduleDto).toResponseEntity(SuccessCode.OK);
    }

    @PostMapping(path = "/v1/schedules")
    public ResponseEntity<SuccessResponse> create(@RequestBody @Validated ScheduleRequest scheduleRequest){
        ScheduleDto result = scheduleService.insert(new ScheduleDto(scheduleRequest));
        scheduleChangeDataSpreader.create(result);
        return new SuccessResponse(result).toResponseEntity(SuccessCode.CREATED);
    }

    @PutMapping(path = "/v1/schedules")
    public ResponseEntity<SuccessResponse> update(@RequestBody @Validated ScheduleRequest scheduleRequest){
        ScheduleDto result = scheduleService.update(new ScheduleDto(scheduleRequest));
        scheduleChangeDataSpreader.update(result);
        return new SuccessResponse(result).toResponseEntity(SuccessCode.OK);
    }

    @DeleteMapping(path = "/v1/schedules/{id}")
    public ResponseEntity<SuccessResponse> delete(@PathVariable Long id){
        scheduleService.delete(id);
        scheduleChangeDataSpreader.delete(id);
        return new SuccessResponse(null).toResponseEntity(SuccessCode.OK);
    }

    @PutMapping(path = "/v1/schedules/run")
    public ResponseEntity<SuccessResponse> runUpdate(@RequestBody @Validated ScheduleRunRequest scheduleRunRequest){
        ScheduleRunDto scheduleRunDto = new ScheduleRunDto(scheduleRunRequest);
        scheduleService.updateRun(scheduleRunDto);
        return new SuccessResponse(scheduleRunDto).toResponseEntity(SuccessCode.OK);
    }
}
