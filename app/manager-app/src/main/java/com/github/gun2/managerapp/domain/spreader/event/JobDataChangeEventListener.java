package com.github.gun2.managerapp.domain.spreader.event;

import com.github.gun2.managerapp.domain.job.event.JobDataChangeEvent;
import com.github.gun2.managerapp.domain.spreader.JobChangeDataSpreader;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 업무 도메인의 변경사항을 전파하는 리스너
 */
@Component
public class JobDataChangeEventListener {
    private final JobChangeDataSpreader jobChangeDataSpreader;

    public JobDataChangeEventListener(JobChangeDataSpreader jobChangeDataSpreader) {
        this.jobChangeDataSpreader = jobChangeDataSpreader;
    }

    @EventListener(JobDataChangeEvent.class)
    void jobDataChangeEventListener(JobDataChangeEvent event) {
        switch (event.getType()){
            case CREATE -> {
                jobChangeDataSpreader.create(event.getData());
            }
            case UPDATE -> {
                jobChangeDataSpreader.update(event.getData());
            }
            case DELETE -> {
                jobChangeDataSpreader.delete(event.getData().getId());
            }
        }
    }
}
