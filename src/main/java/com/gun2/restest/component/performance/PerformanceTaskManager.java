package com.gun2.restest.component.performance;

import com.gun2.restest.dto.JobDto;
import com.gun2.restest.dto.PerformanceDto;
import com.gun2.restest.exception.PerformanceIsAlreadyRunningException;
import com.gun2.restest.exception.PerformanceIsNotRunningException;
import com.gun2.restest.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * <p>성능측정 정보 관리 컨포넌트</p>
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PerformanceTaskManager {
    private final JobService jobService;
    private final PerformanceDataBlockingQueue performanceDataBlockingQueue;
    private Map<String, PerformanceTaskGroup> performanceTaskGroupMap = new HashMap<>();

    /**
     * <p>performance를 수행하기 위한 객체들을 생성</p>
     * @param jobIdList
     * @return
     */
    public PerformanceDto create(List<Long> jobIdList, int instance){
        String uuid = UUID.randomUUID().toString();
        List<JobDto> jobDtoList = jobIdList.stream().map(jobService::findById).toList();
        List<PerformanceTask> performanceTaskList = jobDtoList.stream().map(jobDto -> {
            PerformanceTask performanceTask = new PerformanceTask(jobDto, instance);
            return performanceTask;
        }).toList();
        PerformanceTaskGroup performanceTaskGroup = new PerformanceTaskGroup(uuid, performanceTaskList, performanceDataBlockingQueue);
        performanceTaskGroupMap.put(uuid, performanceTaskGroup);
        return new PerformanceDto(instance, jobDtoList, uuid);
    }

    public void start(String uuid){
        PerformanceTaskGroup performanceTaskGroup = performanceTaskGroupMap.get(uuid);
        if(!performanceTaskGroup.isRun()){
            performanceTaskGroup.start();
            new Thread(() -> {
                try {
                    Thread.sleep(60000);
                }catch (Exception e){
                    log.error("reservation stop error : ", e);
                }
                stop(uuid);
            }).start();
        }else {
            throw new PerformanceIsAlreadyRunningException();
        }
    }

    public void stop(String uuid){
        PerformanceTaskGroup performanceTaskGroup = performanceTaskGroupMap.get(uuid);
        if(performanceTaskGroup.isRun()){
            performanceTaskGroup.stop(uuid);
        }else {
            throw new PerformanceIsNotRunningException();
        }
    }

}
