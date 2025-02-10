package com.github.gun2.managerapp.domain.performance.component;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
@Getter
public class PerformanceDataBlockingQueue {
    private BlockingQueue<PerformanceData> performanceDataBlockingQue = new LinkedBlockingQueue();
    private BlockingQueue<String> performanceStopBlockingQue = new LinkedBlockingQueue();

    public void addData(PerformanceData performanceData){
        performanceDataBlockingQue.add(performanceData);
    }
    public void addStop(String uuid){performanceStopBlockingQue.add(uuid);}
}
