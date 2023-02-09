package com.gun2.restest.component.performance;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>성능측정 타겟들 정보 및 기록</p>
 */
@RequiredArgsConstructor
@Getter
public class PerformanceTaskGroup {
    private final String uuid;
    private final PerformanceTaskMeasurer performanceTaskMeasurer;
    private List<PerformanceTask> performanceTaskList = new ArrayList<>();
    private final PerformanceDataBlockingQueue performanceDataBlockingQueue;
    private boolean run = false;

    public PerformanceTaskGroup(String uuid, List<PerformanceTask> performanceTaskList, PerformanceDataBlockingQueue performanceDataBlockingQueue) {
        this.uuid = uuid;
        this.performanceTaskMeasurer = new PerformanceTaskMeasurer(uuid, performanceTaskList, performanceDataBlockingQueue);
        this.performanceTaskList = performanceTaskList;
        this.performanceDataBlockingQueue = performanceDataBlockingQueue;
    }

    public void start() {
        this.run = true;
        startTask();
        startMeasurer();
    }

    public void startTask() {
        this.performanceTaskList.forEach(performanceTask -> {
            performanceTask.start();
        });
    }

    public void stopTask(){
        this.performanceTaskList.forEach(performanceTask -> {
            performanceTask.stop();
        });
    }

    public void waitStopTask(){
        this.performanceTaskList.forEach(performanceTask -> performanceTask.join());
    }

    public void startMeasurer() {
        this.performanceTaskMeasurer.start();
    }

    public void stopMeasurer(){
        this.performanceTaskMeasurer.stop();
    }

    public void waitStopMeasurer(){
        this.performanceTaskMeasurer.join();
    }

    public void stop(String uuid){
        stopTask();
        stopMeasurer();
        waitStopTask();
        waitStopMeasurer();
        spreadStopFlag(uuid);
        this.run = false;
    }

    /**
     * <p>종료 정보를 큐에 전달</p>
     * @param uuid
     */
    public void spreadStopFlag(String uuid){
        performanceDataBlockingQueue.addStop(uuid);
    }

}
