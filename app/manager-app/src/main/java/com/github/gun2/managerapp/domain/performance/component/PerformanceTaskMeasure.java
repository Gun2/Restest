package com.github.gun2.managerapp.domain.performance.component;

import lombok.Getter;
import lombok.ToString;

/**
 * <p>성능 측정 항목</p>
 */
@Getter
@ToString
public class PerformanceTaskMeasure {
    private Long jobId;
    private long runTime;
    private int cnt;
    private int rpm;
    private long minTime;
    private long maxTime;
    private int successCnt;
    private int failureCnt;

    public PerformanceTaskMeasure(Long jobId,
                                  long runTime,
                                  int cnt,
                                  int rpm,
                                  long minTime,
                                  long maxTime,
                                  int successCnt,
                                  int failureCnt){
        this.jobId = jobId;
        this.runTime = runTime;
        this.cnt = cnt;
        this.rpm = rpm;
        this.minTime = minTime;
        this.maxTime = maxTime;
        this.successCnt = successCnt;
        this.failureCnt = failureCnt;
    }

    public static PerformanceTaskMeasure ofEmpty(Long jobId){
        return new PerformanceTaskMeasure(jobId, 0,0,0,0,0,0, 0);
    }
}
