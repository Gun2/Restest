package com.github.gun2.managerapp.domain.schedule.entity;


import com.github.gun2.managerapp.domain.job.entity.Job;
import lombok.*;

import jakarta.persistence.*;

@Entity
@Getter
@ToString
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "scheduleId")
    private Schedule schedule = new Schedule();

    @ManyToOne
    @JoinColumn(name = "jobId")
    private Job job = new Job();

    private Integer sort;

    @Builder
    public ScheduleJob(Long id, Schedule schedule, Job job, Integer sort) {
        this.id = id;
        this.schedule = schedule;
        this.job = job;
        this.sort = sort;
    }
}
