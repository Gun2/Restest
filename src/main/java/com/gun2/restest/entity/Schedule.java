package com.gun2.restest.entity;

import io.swagger.models.auth.In;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "schedule")
    private List<ScheduleJob> scheduleJobList = new ArrayList<>();

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    @Builder
    public Schedule(Long id, String title, Integer delay) {
        this.id = id;
        this.title = title;
        this.delay = delay;
    }

    public void setScheduleJobList(List<ScheduleJob> scheduleJobList){
        this.scheduleJobList = scheduleJobList;
    }
}
