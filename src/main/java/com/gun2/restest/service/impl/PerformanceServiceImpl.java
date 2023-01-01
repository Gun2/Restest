package com.gun2.restest.service.impl;

import com.gun2.restest.component.performance.PerformanceTaskManager;
import com.gun2.restest.dto.PerformanceDto;
import com.gun2.restest.form.request.PerformanceCreateRequest;
import com.gun2.restest.service.PerformanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
@RequiredArgsConstructor
@Slf4j
public class PerformanceServiceImpl implements PerformanceService {

    @Value("${max-performance-test-instance:10}")
    private int maxPerformanceTestInstance;

    @Value("${max-performance-test-job:5}")
    private int masPerformanceTestJob;

    private final PerformanceTaskManager performanceTaskManager;

    @Override
    public int getMaxInstance(){
        return maxPerformanceTestInstance;
    }

    @Override
    public int getMaxJob(){
        return masPerformanceTestJob;
    }

    @Override
    public void validate(PerformanceCreateRequest performanceCreateRequest, BindingResult bindingResult){
        if (performanceCreateRequest.getInstance() > getMaxInstance()){
            bindingResult.addError(new FieldError("performanceRequest", "instance", "최대 개수를 초과합니다."));
        }
        if(performanceCreateRequest.getJobIdList().size() > getMaxJob()){
            bindingResult.addError(new FieldError("performanceRequest", "jobIdList", "최대 개수를 초과합니다."));
        }
    }

    @Override
    public PerformanceDto create(PerformanceCreateRequest performanceCreateRequest){
        return performanceTaskManager.create(performanceCreateRequest.getJobIdList(), performanceCreateRequest.getInstance());
    }

    @Override
    public void start(String uuid){
        performanceTaskManager.start(uuid);
    }

    @Override
    public void stop(String uuid){
        performanceTaskManager.stop(uuid);
    }
}
