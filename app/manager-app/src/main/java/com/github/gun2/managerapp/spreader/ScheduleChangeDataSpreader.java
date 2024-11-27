package com.github.gun2.managerapp.spreader;


import com.github.gun2.managerapp.dto.ScheduleDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

/**
 * <p>schedule 정보 변경 사항을 클라이언트에게 전달하는 컴포넌트</p>
 */
@Component
@Slf4j
public class ScheduleChangeDataSpreader extends ChangeDataSpreader<ScheduleDto, Long> {
    ScheduleChangeDataSpreader(SimpMessageSendingOperations simpMessageSendingOperations){
        super(simpMessageSendingOperations, "/change-data-spreader/schedule");
    }
}
