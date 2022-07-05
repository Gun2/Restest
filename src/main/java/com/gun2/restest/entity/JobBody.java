package com.gun2.restest.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

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

    private String body;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @CreationTimestamp
    private LocalDateTime updateAt;

    @Builder
    public JobBody(Long id, String body, LocalDateTime createdAt, LocalDateTime updateAt) {
        this.id = id;
        this.body = body;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }
}
