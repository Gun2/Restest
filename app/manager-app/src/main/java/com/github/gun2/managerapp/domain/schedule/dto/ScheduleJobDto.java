package com.github.gun2.managerapp.domain.schedule.dto;

import com.github.gun2.managerapp.domain.job.dto.JobDto;
import com.github.gun2.managerapp.domain.schedule.entity.ScheduleJob;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ScheduleJobDto implements Serializable {
    private Long id;
    private ScheduleDto scheduleDto;
    private JobDto jobDto;
    private Integer sort;

    public ScheduleJobDto(ScheduleJob scheduleJob) {
        this.id = scheduleJob.getId();
        this.scheduleDto = new ScheduleDto(scheduleJob.getSchedule());
        this.jobDto = new JobDto(scheduleJob.getJob());
        this.sort = scheduleJob.getSort();
    }

    public ScheduleJob toEntity(){
        return ScheduleJob.builder()
                .id(this.id)
                .job(this.jobDto.toEntity())
                .schedule(this.scheduleDto.toEntity())
                .sort(this.sort)
                .build();
    }
}
