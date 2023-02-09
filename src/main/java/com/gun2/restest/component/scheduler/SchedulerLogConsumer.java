package com.gun2.restest.component.scheduler;

import com.gun2.restest.controller.rest.SchedulerRestController;
import com.gun2.restest.dto.SchedulerLogDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@Getter
public class SchedulerLogConsumer {

    private final SchedulerLogBlockingQueue schedulerLogBlockingQueue;
    private final Thread successLogConsumer;
    private final Thread failureLogConsumer;
    private final SchedulerRestController schedulerRestController;

    public SchedulerLogConsumer(SchedulerLogBlockingQueue schedulerLogBlockingQueue, SchedulerRestController schedulerRestController) {
        this.schedulerLogBlockingQueue = schedulerLogBlockingQueue;
        this.schedulerRestController = schedulerRestController;
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
                        schedulerRestController.pushSucceedResponse(schedulerLogDto.getSchedulerInfo().getScheduleDto().getId(),
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
                        schedulerRestController.pushFailedResponse(schedulerLogDto.getSchedulerInfo().getScheduleDto().getId(),
                                schedulerLogDto.getHttpResponseDto());
                    }
                } catch (Exception e) {
                    log.error("schedulerFailureLogDtoBlockingQueue : ", e);
                }
            }
        });
    }

}
