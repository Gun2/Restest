package com.gun2.restest.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobHeader {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    private String keyName;
    private String value;
    private String description;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    //사용유무
    private boolean usable;

    @Builder
    public JobHeader(Long id, Job job, String keyName, String value, String description, boolean usable) {
        this.id = id;
        this.job = job;
        this.keyName = keyName;
        this.value = value;
        this.description = description;
        this.usable = usable;
    }

    public void updateJob(Job job){
        this.job = job;
    }
}
