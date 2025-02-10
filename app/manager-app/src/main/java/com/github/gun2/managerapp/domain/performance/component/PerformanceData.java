package com.github.gun2.managerapp.domain.performance.component;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PerformanceData {
    private String uuid;
    //key : jobId, value : 측정 값
    private List<PerformanceTaskMeasure> performanceTaskMeasureList;
    //측정 시간
    private long measureTime;

    public PerformanceData(String uuid, List<PerformanceTaskMeasure> performanceTaskMeasureList, long measureTime){
        this.uuid = uuid;
        this.performanceTaskMeasureList = performanceTaskMeasureList;
        this.measureTime = measureTime;
    }
}
