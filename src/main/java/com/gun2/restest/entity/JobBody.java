package com.gun2.restest.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
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
    private Long jobId;
    private String body;
    private Integer afterDelay;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    @Builder
    public JobBody(Long id, Long jobId, String body, Integer afterDelay, LocalDateTime createdAt, LocalDateTime updateAt) {
        this.id = id;
        this.jobId = jobId;
        this.body = body;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }
}
