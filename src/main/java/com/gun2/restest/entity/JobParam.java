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
public class JobParam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String keyName;
    private String value;
    private String description;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    @Builder
    public JobParam(Long id, String keyName, String value, String description, LocalDateTime createdAt, LocalDateTime updateAt) {
        this.id = id;
        this.keyName = keyName;
        this.value = value;
        this.description = description;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }
}
