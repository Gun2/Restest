package com.github.gun2.managerapp.domain.schedule.dto;

import com.github.gun2.managerapp.domain.job.dto.JobDto;
import com.github.gun2.managerapp.domain.schedule.entity.Schedule;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto implements Serializable {
    private Long id;
    private String title;
    private Integer delay;

    private boolean run;

    private List<Long> jobIdList = new ArrayList<>();
    private List<JobDto> jobList = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ScheduleDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.delay = schedule.getDelay();
        this.jobList = schedule.getScheduleJobList().stream().map(s -> new JobDto(s.getJob())).toList();
        this.jobIdList = schedule.getScheduleJobList().stream().map(s -> s.getJob().getId()).toList();
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
        this.run = schedule.isRun();
    }

    public ScheduleDto(ScheduleRequest scheduleRequest) {
        this.id = scheduleRequest.getId();
        this.title = scheduleRequest.getTitle();
        this.delay = scheduleRequest.getDelay();
        this.jobList = scheduleRequest.getJobList();
        this.jobIdList = scheduleRequest.getJobIdList();
        this.run = scheduleRequest.isRun();
    }

    public Schedule toEntity(){
        return Schedule.builder()
                .id(this.id)
                .title(this.title)
                .delay(this.delay)
                .run(this.run)
                .build();
    }
}
