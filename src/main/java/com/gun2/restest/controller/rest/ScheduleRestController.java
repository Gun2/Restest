package com.gun2.restest.controller.rest;

import com.gun2.restest.constant.SuccessCode;
import com.gun2.restest.dto.ScheduleDto;
import com.gun2.restest.dto.ScheduleRunDto;
import com.gun2.restest.form.response.SuccessResponse;
import com.gun2.restest.service.ScheduleService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    @ApiOperation(value = "모든 스케줄 조회", notes="스케줄 테이블의 모든 값을 반환")
    @GetMapping(path = "/v1/schedules")
    public ResponseEntity findAll(){
        List<ScheduleDto> items = scheduleService.findAll();
        return new SuccessResponse(items).toResponseEntity(SuccessCode.OK);
    }

    @GetMapping(path = "/v1/schedules/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        ScheduleDto scheduleDto = scheduleService.findById(id);
        return new SuccessResponse(scheduleDto).toResponseEntity(SuccessCode.OK);
    }

    @PostMapping(path = "/v1/schedules")
    public ResponseEntity create(@RequestBody @Validated ScheduleDto scheduleDto){
        ScheduleDto result = scheduleService.insert(scheduleDto);
        return new SuccessResponse(result).toResponseEntity(SuccessCode.CREATED);
    }

    @PutMapping(path = "/v1/schedules")
    public ResponseEntity update(@RequestBody @Validated ScheduleDto scheduleDto){
        ScheduleDto result = scheduleService.update(scheduleDto);
        return new SuccessResponse(result).toResponseEntity(SuccessCode.OK);
    }

    @DeleteMapping(path = "/v1/schedules/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        scheduleService.delete(id);
        return new SuccessResponse(null).toResponseEntity(SuccessCode.OK);
    }

    @PutMapping(path = "/v1/schedules/run")
    public ResponseEntity runUpdate(@RequestBody @Validated ScheduleRunDto scheduleRunDto){

        scheduleService.updateRun(scheduleRunDto);
        return new SuccessResponse(scheduleRunDto).toResponseEntity(SuccessCode.OK);
    }

}
