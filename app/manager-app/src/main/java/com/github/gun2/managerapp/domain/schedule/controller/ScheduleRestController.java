package com.github.gun2.managerapp.domain.schedule.controller;

import com.github.gun2.managerapp.constant.SuccessCode;
import com.github.gun2.managerapp.domain.spreader.ScheduleChangeDataSpreader;
import com.github.gun2.managerapp.domain.schedule.dto.ScheduleDto;
import com.github.gun2.managerapp.domain.scheduler.dto.ScheduleRunDto;
import com.github.gun2.managerapp.domain.schedule.dto.ScheduleRequest;
import com.github.gun2.managerapp.domain.schedule.dto.ScheduleRunRequest;
import com.github.gun2.managerapp.form.response.SuccessResponse;
import com.github.gun2.managerapp.domain.schedule.service.ScheduleService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ScheduleRestController {
    private final ScheduleService scheduleService;

    @ApiOperation(value = "모든 스케줄 조회", notes="스케줄 테이블의 모든 값을 반환")
    @GetMapping(path = "/v1/schedules")
    public ResponseEntity<SuccessResponse<List<ScheduleDto>>> findAll(){
        List<ScheduleDto> items = scheduleService.findAll();
        return new SuccessResponse(items).toResponseEntity(SuccessCode.OK);
    }

    @GetMapping(path = "/v1/schedules/{id}")
    public ResponseEntity<SuccessResponse<ScheduleDto>> findById(@PathVariable("id") Long id){
        ScheduleDto scheduleDto = scheduleService.findById(id);
        return new SuccessResponse(scheduleDto).toResponseEntity(SuccessCode.OK);
    }

    @PostMapping(path = "/v1/schedules")
    public ResponseEntity<SuccessResponse<ScheduleDto>> create(@RequestBody @Validated ScheduleRequest scheduleRequest){
        ScheduleDto result = scheduleService.insert(new ScheduleDto(scheduleRequest));
        return new SuccessResponse(result).toResponseEntity(SuccessCode.CREATED);
    }

    @PutMapping(path = "/v1/schedules")
    public ResponseEntity<SuccessResponse<ScheduleDto>> update(@RequestBody @Validated ScheduleRequest scheduleRequest){
        ScheduleDto result = scheduleService.update(new ScheduleDto(scheduleRequest));
        return new SuccessResponse(result).toResponseEntity(SuccessCode.OK);
    }

    @DeleteMapping(path = "/v1/schedules/{id}")
    public ResponseEntity<SuccessResponse<Void>> delete(@PathVariable("id") Long id){
        scheduleService.delete(id);
        return new SuccessResponse(null).toResponseEntity(SuccessCode.OK);
    }

    @PutMapping(path = "/v1/schedules/run")
    public ResponseEntity<SuccessResponse<ScheduleDto>> runUpdate(@RequestBody @Validated ScheduleRunRequest scheduleRunRequest){
        ScheduleRunDto scheduleRunDto = new ScheduleRunDto(scheduleRunRequest);
        scheduleService.updateRun(scheduleRunDto);
        return new SuccessResponse(scheduleRunDto).toResponseEntity(SuccessCode.OK);
    }
}
