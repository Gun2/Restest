package com.github.gun2.managerapp.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobBody {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Job job;
    @Column(length = 4096)
    private String body;
    private Integer afterDelay;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    private boolean usable;

    @Builder
    public JobBody(Long id, Job job, String body, Integer afterDelay, boolean usable) {
        this.id = id;
        this.body = body;
        this.job = job;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
        this.usable = usable;
    }

    public void updateJob(Job job){
        this.job = job;
    }
}
