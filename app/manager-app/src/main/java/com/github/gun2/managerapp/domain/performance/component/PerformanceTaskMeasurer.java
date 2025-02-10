package com.github.gun2.managerapp.domain.performance.component;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@Slf4j
public class PerformanceTaskMeasurer {
    private final String uuid;
    private final List<PerformanceTask> performanceTaskList;
    private final Thread runThread;
    private final long delay = 300;
    private final Map<Long, List<PerformanceTaskMeasure>> performanceTaskMeasureListMap = new HashMap<>();
    private long startTime;

    public PerformanceTaskMeasurer(String uuid, List<PerformanceTask> performanceTaskList, PerformanceDataBlockingQueue performanceDataBlockingQueue) {
        this.uuid = uuid;
        this.performanceTaskList = performanceTaskList;
        initMeasureMap(performanceTaskList);
        this.runThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()){
                List<PerformanceTaskMeasure> performanceTaskMeasureList = new ArrayList<>();
                long measureTime = System.currentTimeMillis() - startTime;
                performanceTaskList.forEach(performanceTask -> {
                    long currentTime = System.currentTimeMillis();
                    int cnt = performanceTask.getTotalRequestCount();
                    long runTime = currentTime - performanceTask.getStartTimeMillisecond();

                    Long jobId = performanceTask.getJobDto().getId();
                    List<PerformanceTaskMeasure> historyPerformanceTaskMeasureList = performanceTaskMeasureListMap.get(jobId);

                    PerformanceTaskMeasure last;
                    if(historyPerformanceTaskMeasureList.size() == 0){
                        last = PerformanceTaskMeasure.ofEmpty(jobId);
                    }else{
                        last = historyPerformanceTaskMeasureList.get(historyPerformanceTaskMeasureList.size() - 1);
                    }

                    long duringTime = runTime - last.getRunTime();
                    long duringCnt = cnt - last.getCnt();
                    int rpm = calcRpm(duringTime, duringCnt);
                    PerformanceTaskMeasure performanceTaskMeasure = new PerformanceTaskMeasure(jobId,
                            runTime,
                            cnt,
                            rpm,
                            performanceTask.getMinMillisecond(),
                            performanceTask.getMaxMillisecond(),
                            performanceTask.getSuccessCount().get(),
                            performanceTask.getFailureCount().get());
                    historyPerformanceTaskMeasureList.add(performanceTaskMeasure);
                    performanceTaskMeasureList.add(performanceTaskMeasure);
                });
                performanceDataBlockingQueue.addData(new PerformanceData(uuid, performanceTaskMeasureList, measureTime));
                try {
                    Thread.sleep(delay);
                }catch (InterruptedException e){
                    log.error("",e);
                    break;
                }catch (Exception e){
                    log.error("",e);
                }
            }
        });
    }

    /**
     * <p>측정 대상 객체 초기화</p>
     * @param performanceTaskList
     */
    public void initMeasureMap(List<PerformanceTask> performanceTaskList){
        for (PerformanceTask performanceTask : performanceTaskList){
            performanceTaskMeasureListMap.put(performanceTask.getJobDto().getId(), new ArrayList<>());
        }
    }

    public void start(){
        if(!runThread.isAlive()){
            runThread.start();
            startTime = System.currentTimeMillis();
        }

    }

    public void stop(){
        if(runThread.isAlive()){
            runThread.interrupt();
        }
    }

    public void join(){
        try {
            runThread.join(60000);
        }catch (Exception e){
            log.error("join error : ", e);
        }
    }

    /**
     * <p>rpm 계산</p>
     * @param duringTime
     * @param duringCnt
     * @return
     */
    public int calcRpm(long duringTime, long duringCnt){
        if (duringTime != 0){
            long minuteMillisecond = 1000 * 60;
            int result = (int) (minuteMillisecond / duringTime * duringCnt);
            return result;
        }
        return 0;
    }
}
