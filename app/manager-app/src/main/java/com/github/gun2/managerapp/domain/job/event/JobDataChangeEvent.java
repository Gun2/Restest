package com.github.gun2.managerapp.domain.job.event;

import com.github.gun2.managerapp.domain.job.dto.JobDto;
import com.github.gun2.managerapp.event.DataChangeEvent;

/**
 * Job 도메인 변경 시 발생하는 이벤트
 */
public class JobDataChangeEvent extends DataChangeEvent<JobDto> {

    public JobDataChangeEvent(JobDto data, Type type) {
        super(data, type);
    }
}
