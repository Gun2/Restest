package com.gun2.restest.dto;

import com.gun2.restest.entity.Job;
import com.gun2.restest.entity.Schedule;
import com.gun2.restest.entity.ScheduleJob;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ScheduleDto implements Serializable {
    private Long id;
    @NotBlank
    private String title;
    @Range(min = 0, max = 86400)
    private Integer delay;

    private boolean run;

    private List<Long> jobIdList = new ArrayList<>();
    private List<JobDto> jobList = new ArrayList<>();

    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public ScheduleDto(Schedule schedule) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.delay = schedule.getDelay();
        this.jobList = schedule.getScheduleJobList().stream().map(s -> new JobDto(s.getJob())).toList();
        this.jobIdList = schedule.getScheduleJobList().stream().map(s -> s.getJob().getId()).toList();
        this.createdAt = schedule.getCreatedAt();
        this.updateAt = schedule.getUpdateAt();
        this.run = schedule.isRun();
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
