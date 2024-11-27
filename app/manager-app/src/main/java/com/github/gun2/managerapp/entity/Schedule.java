package com.github.gun2.managerapp.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Integer delay;
    private boolean run;

    @OneToMany(mappedBy = "schedule")
    private List<ScheduleJob> scheduleJobList = new ArrayList<>();

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    @Builder
    public Schedule(Long id, String title, Integer delay, boolean run) {
        this.id = id;
        this.title = title;
        this.delay = delay;
        this.run = run;
    }

    public void setScheduleJobList(List<ScheduleJob> scheduleJobList){
        this.scheduleJobList = scheduleJobList;
    }
}
