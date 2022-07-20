package com.gun2.restest.controller.rest;

import com.gun2.restest.component.scheduler.SchedulerComponent;
import com.gun2.restest.component.scheduler.SchedulerInfo;
import com.gun2.restest.dto.SchedulerStateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@MessageMapping("/scheduler")
@RequestMapping("/api")
public class SchedulerRestController {

    private final SimpMessageSendingOperations sendingOperations;
    private final SchedulerComponent schedulerComponent;

    public SchedulerRestController(SimpMessageSendingOperations sendingOperations, @Lazy SchedulerComponent schedulerComponent) {
        this.sendingOperations = sendingOperations;
        this.schedulerComponent = schedulerComponent;
    }

    @MessageMapping("/init")
    public void init(String msg){
        List<SchedulerStateDto> schedulerStateDtoList =  schedulerComponent
                .getSchedulerInfoMap()
                .values()
                .stream()
                .map(info -> SchedulerStateDto.of(info)).toList();
        sendingOperations.convertAndSend("/scheduler/init", schedulerStateDtoList);
    }

    public void deleteScheduleInfo(Long id){
        sendingOperations.convertAndSend("/scheduler/delete", id);
    }

    public void insertScheduleInfo(SchedulerInfo schedulerInfo){
        sendingOperations.convertAndSend("/scheduler/insert", SchedulerStateDto.of(schedulerInfo));
    }

    public void updateScheduleInfo(SchedulerInfo schedulerInfo){
        sendingOperations.convertAndSend("/scheduler/update", SchedulerStateDto.of(schedulerInfo));
    }



}
