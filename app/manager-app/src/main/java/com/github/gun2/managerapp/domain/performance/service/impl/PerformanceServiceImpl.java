package com.github.gun2.managerapp.domain.performance.service.impl;

import com.github.gun2.managerapp.domain.performance.component.PerformanceTaskManager;
import com.github.gun2.managerapp.domain.performance.dto.PerformanceDto;
import com.github.gun2.managerapp.domain.performance.service.PerformanceService;
import com.github.gun2.managerapp.domain.performance.dto.PerformanceCreateRequest;
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

    @Value("${app.default.value.max-performance-test-instance:10}")
    private int maxPerformanceTestInstance;

    @Value("${app.default.value.max-performance-test-job:5}")
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
