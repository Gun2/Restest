package com.github.gun2.managerapp.domain.scheduler.component;

import com.github.gun2.managerapp.domain.scheduler.dto.SchedulerLogDto;
import com.github.gun2.managerapp.domain.spreader.SchedulerDataSpreader;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 스케줄러 로그 큐 컨슈머
 */
@Component
@Slf4j
@Getter
public class SchedulerLogConsumer {

    //스케줄러 로그 큐
    private final SchedulerLogBlockingQueue schedulerLogBlockingQueue;
    //성공 로그 컨슈머 스레드
    private final Thread successLogConsumer;
    //실패 로그 컨슈머 스레즈
    private final Thread failureLogConsumer;
    //로그 전파 컴포넌트
    private final SchedulerDataSpreader schedulerDataSpreader;

    public SchedulerLogConsumer(SchedulerLogBlockingQueue schedulerLogBlockingQueue, SchedulerDataSpreader schedulerDataSpreader) {
        this.schedulerLogBlockingQueue = schedulerLogBlockingQueue;
        this.schedulerDataSpreader = schedulerDataSpreader;
        this.successLogConsumer = createSuccessLogConsumer();
        this.failureLogConsumer = createFailureLogConsumer();
        consumersStart();
    }

    /**
     * <p>생성한 컨슈머를 실행</p>
     */
    public void consumersStart(){
        successLogConsumer.start();
        failureLogConsumer.start();
    }


    /**
     * <p>scheduler 성공 로그 컨슈머 스레드 생성</p>
     * @return 컨슈머 스레드
     */
    private Thread createSuccessLogConsumer() {
        return new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    BlockingQueue<SchedulerLogDto> schedulerSuccessLogDtoBlockingQueue
                            = schedulerLogBlockingQueue.getSchedulerSuccessLogDtoBlockingQueue();
                    SchedulerLogDto schedulerLogDto = schedulerSuccessLogDtoBlockingQueue.poll(1000, TimeUnit.MILLISECONDS);
                    if (schedulerLogDto != null){
                        schedulerLogDto.getSchedulerInfo().recordSucceedResponse(schedulerLogDto.getHttpResponseDto());
                        schedulerDataSpreader.pushSucceedResponse(schedulerLogDto.getSchedulerInfo().getScheduleDto().getId(),
                                schedulerLogDto.getHttpResponseDto());
                    }
                } catch (Exception e) {
                    log.error("schedulerSuccessLogDtoBlockingQueue : ", e);
                }
            }
        });
    }

    /**
     * <p>scheduler 실패 로그 컨슈머 스레드 생성</p>
     * @return 컨슈머 스레드
     */
    private Thread createFailureLogConsumer() {
        return new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    BlockingQueue<SchedulerLogDto> schedulerFailureLogDtoBlockingQueue
                            = schedulerLogBlockingQueue.getSchedulerFailureLogDtoBlockingQueue();
                    SchedulerLogDto schedulerLogDto = schedulerFailureLogDtoBlockingQueue.poll(1000, TimeUnit.MILLISECONDS);
                    if(schedulerLogDto != null){
                        schedulerLogDto.getSchedulerInfo().recordFailureResponse(schedulerLogDto.getHttpResponseDto());
                        schedulerDataSpreader.pushFailedResponse(schedulerLogDto.getSchedulerInfo().getScheduleDto().getId(),
                                schedulerLogDto.getHttpResponseDto());
                    }
                } catch (Exception e) {
                    log.error("schedulerFailureLogDtoBlockingQueue : ", e);
                }
            }
        });
    }

}
