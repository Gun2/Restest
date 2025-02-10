package com.github.gun2.managerapp.domain.spreader;

import com.github.gun2.managerapp.domain.scheduler.component.SchedulerInfo;
import com.github.gun2.managerapp.domain.scheduler.dto.HttpResponseDto;
import com.github.gun2.managerapp.domain.scheduler.dto.SchedulerStateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

/**
 * 스케줄러 정보를 websocket을 통해 전파하는 클래스
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulerDataSpreader {
    private final SimpMessageSendingOperations sendingOperations;

    public void deleteScheduleInfo(Long id){
        sendingOperations.convertAndSend("/scheduler/delete", id);
    }

    public void insertScheduleInfo(SchedulerInfo schedulerInfo){
        sendingOperations.convertAndSend("/scheduler/insert", SchedulerStateDto.of(schedulerInfo));
    }

    public void updateScheduleInfo(SchedulerInfo schedulerInfo){
        sendingOperations.convertAndSend("/scheduler/update", SchedulerStateDto.of(schedulerInfo));
    }

    public void pushFailedResponse(Long scheduleId, HttpResponseDto httpResponseDto){
        sendingOperations.convertAndSend("/scheduler/"+scheduleId+"/failure", httpResponseDto);
    }
    public void pushSucceedResponse(Long scheduleId, HttpResponseDto httpResponseDto){
        sendingOperations.convertAndSend("/scheduler/"+scheduleId+"/success", httpResponseDto);
    }
}
