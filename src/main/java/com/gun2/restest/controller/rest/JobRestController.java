package com.gun2.restest.controller.rest;

import com.gun2.restest.constant.SuccessCode;
import com.gun2.restest.dto.JobDto;
import com.gun2.restest.form.request.JobRequest;
import com.gun2.restest.form.response.SuccessResponse;
import com.gun2.restest.service.JobService;
import com.gun2.restest.spreader.JobChangeDataSpreader;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class JobRestController {
    private final JobService jobService;
    private final JobChangeDataSpreader jobChangeDataSpreader;


    @ApiOperation(value = "모든 업무관리 조회", notes="업무관리 테이블의 모든 값을 반환")
    @GetMapping(path = "/v1/jobs")
    public ResponseEntity<SuccessResponse> findAll(){
        List<JobDto> items = jobService.findAll();
        return new SuccessResponse(items).toResponseEntity(SuccessCode.OK);
    }

    @GetMapping(path = "/v1/jobs/{id}")
    public ResponseEntity<SuccessResponse> findById(@PathVariable Long id){
        JobDto jobDto = jobService.findById(id);
        return new SuccessResponse(jobDto).toResponseEntity(SuccessCode.OK);
    }

    @PostMapping(path = "/v1/jobs")
    public ResponseEntity<SuccessResponse> create(@RequestBody @Validated JobRequest jobRequest){
        JobDto result = jobService.insert(new JobDto(jobRequest));
        jobChangeDataSpreader.create(result);
        return new SuccessResponse(result).toResponseEntity(SuccessCode.CREATED);
    }

    @PutMapping(path = "/v1/jobs")
    public ResponseEntity<SuccessResponse> update(@RequestBody @Validated JobRequest jobRequest){
        JobDto result = jobService.update(new JobDto(jobRequest));
        jobChangeDataSpreader.update(result);
        return new SuccessResponse(result).toResponseEntity(SuccessCode.OK);
    }

    @DeleteMapping(path = "/v1/jobs/{id}")
    public ResponseEntity<SuccessResponse> delete(@PathVariable Long id){
        jobService.delete(id);
        jobChangeDataSpreader.delete(id);
        return new SuccessResponse(null).toResponseEntity(SuccessCode.OK);
    }

}
