package com.gun2.restest.component.performance;

import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

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
