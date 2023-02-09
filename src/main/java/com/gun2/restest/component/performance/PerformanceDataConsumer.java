package com.gun2.restest.component.performance;

import com.gun2.restest.controller.rest.PerformanceRestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class PerformanceDataConsumer {
    private final PerformanceDataBlockingQueue performanceDataBlockingQueue;
    private final Thread dataConsumerThread;
    private final Thread stopConsumerThreadd;
    private final PerformanceRestController performanceRestController;

    public PerformanceDataConsumer(PerformanceDataBlockingQueue performanceDataBlockingQueue, PerformanceRestController performanceRestController) {
        this.performanceDataBlockingQueue = performanceDataBlockingQueue;
        this.performanceRestController = performanceRestController;
        this.dataConsumerThread = createDataConsumerThread();
        this.stopConsumerThreadd = createStopConsumerThread();
        start();
    }

    public Thread createDataConsumerThread(){
        BlockingQueue<PerformanceData> performanceDataBlockingQue =
                performanceDataBlockingQueue.getPerformanceDataBlockingQue();
        return new Thread(() -> {
           while (!Thread.currentThread().isInterrupted()){
               try {
                   PerformanceData performanceData = performanceDataBlockingQue.poll(100, TimeUnit.MILLISECONDS);
                   if(performanceData != null){
                       performanceRestController.pushPerformanceData(performanceData);
                       log.trace("performanceData : {} cnt : {}", performanceData.toString(), performanceDataBlockingQue.size());
                   }
               }catch (Exception e){
                   log.error("PerformanceDataConsumer : error", e);
               }
           }
        });
    }

    public Thread createStopConsumerThread(){
        BlockingQueue<String> performanceStopBlockingQue =
                performanceDataBlockingQueue.getPerformanceStopBlockingQue();
        return new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()){
                try {
                    String uuid = performanceStopBlockingQue.poll(100, TimeUnit.MILLISECONDS);
                    if(uuid != null){
                        performanceRestController.pushPerformanceStop(uuid);
                        log.info("[stop] uuid : {}", uuid);
                    }
                }catch (Exception e){
                    log.error("StopConsumerThread : error", e);
                }
            }
        });
    }

    public void start(){
        dataConsumerThread.start();
        stopConsumerThreadd.start();
    }
}
