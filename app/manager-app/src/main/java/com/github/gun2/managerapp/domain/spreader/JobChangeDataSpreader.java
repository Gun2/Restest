package com.github.gun2.managerapp.domain.spreader;


import com.github.gun2.managerapp.domain.job.dto.JobDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

/**
 * <p>job 정보 변경 사항을 클라이언트에게 전달하는 컴포넌트</p>
 */
@Component
@Slf4j
public class JobChangeDataSpreader extends ChangeDataSpreader<JobDto, Long> {
    JobChangeDataSpreader(SimpMessageSendingOperations simpMessageSendingOperations){
        super(simpMessageSendingOperations, "/change-data-spreader/job");
    }
}
