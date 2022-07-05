package com.gun2.restest.controller.rest;

import com.gun2.restest.constant.SuccessCode;
import com.gun2.restest.dto.JobDto;
import com.gun2.restest.form.response.SuccessResponse;
import com.gun2.restest.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(path = "/v1/job")
    public ResponseEntity findAll(){
        List<JobDto> items = jobService.findAll();
        return new SuccessResponse(items).toResponseEntity(SuccessCode.OK);
    }

    @GetMapping(path = "/v1/job/{id}")
    public ResponseEntity findById(@PathVariable Long id){
        JobDto jobDto = jobService.findById(id);
        return new SuccessResponse(jobDto).toResponseEntity(SuccessCode.OK);
    }

    @PostMapping(path = "/v1/job")
    public ResponseEntity create(@RequestBody @Validated JobDto jobDto){
        JobDto result = jobService.insert(jobDto);
        return new SuccessResponse(result).toResponseEntity(SuccessCode.CREATED);
    }

    @PutMapping(path = "/v1/job")
    public ResponseEntity update(@RequestBody @Validated JobDto jobDto){
        JobDto result = jobService.update(jobDto);
        return new SuccessResponse(result).toResponseEntity(SuccessCode.OK);
    }

    @DeleteMapping(path = "/v1/job/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        jobService.delete(id);
        return new SuccessResponse(null).toResponseEntity(SuccessCode.OK);
    }

}
