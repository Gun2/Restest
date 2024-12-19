/**
 * performance 결과 데이터 메트릭
 */
export type PerformanceData = {
    uuid: string;
    performanceTaskMeasureList: PerformanceTaskMeasure[];
    //측정기간
    measureTime: number;
}

/**
 * job별로 성능 측정 데이터 메트릭
 */
export type PerformanceTaskMeasure = {
    jobId: number;
    runTime: number;
    cnt: number;
    rpm: number;
    minTime: number;
    maxTime: number;
    successCnt: number;
    failureCnt: number;
}