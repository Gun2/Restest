package com.github.gun2.managerapp.domain.job.controller;

import com.github.gun2.managerapp.constant.SuccessCode;
import com.github.gun2.managerapp.domain.job.dto.JobDto;
import com.github.gun2.managerapp.domain.job.dto.JobRequest;
import com.github.gun2.managerapp.domain.job.service.JobService;
import com.github.gun2.managerapp.form.response.SuccessResponse;
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
public class JobRestController {
    private final JobService jobService;


    @ApiOperation(value = "모든 업무관리 조회", notes="업무관리 테이블의 모든 값을 반환")
    @GetMapping(path = "/v1/jobs")
    public ResponseEntity<SuccessResponse<List<JobDto>>> findAll(){
        List<JobDto> items = jobService.findAll();
        return new SuccessResponse(items).toResponseEntity(SuccessCode.OK);
    }

    @GetMapping(path = "/v1/jobs/{id}")
    public ResponseEntity<SuccessResponse<JobDto>> findById(@PathVariable("id") Long id){
        JobDto jobDto = jobService.findById(id);
        return new SuccessResponse(jobDto).toResponseEntity(SuccessCode.OK);
    }

    @PostMapping(path = "/v1/jobs")
    public ResponseEntity<SuccessResponse<JobDto>> create(@RequestBody @Validated JobRequest jobRequest){
        JobDto result = jobService.insert(new JobDto(jobRequest));
        return new SuccessResponse(result).toResponseEntity(SuccessCode.CREATED);
    }

    @PutMapping(path = "/v1/jobs")
    public ResponseEntity<SuccessResponse<JobDto>> update(@RequestBody @Validated JobRequest jobRequest){
        JobDto result = jobService.update(new JobDto(jobRequest));
        return new SuccessResponse(result).toResponseEntity(SuccessCode.OK);
    }

    @DeleteMapping(path = "/v1/jobs/{id}")
    public ResponseEntity<SuccessResponse<Void>> delete(@PathVariable("id") Long id){
        jobService.delete(id);
        return new SuccessResponse(null).toResponseEntity(SuccessCode.OK);
    }

}
