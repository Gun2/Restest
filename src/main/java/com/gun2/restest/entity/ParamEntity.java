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
public class ParamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String key;
    private String value;
    private String description;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @CreationTimestamp
    private LocalDateTime updateAt;

    @Builder
    public ParamEntity(Long id, String key, String value, String description, LocalDateTime createdAt, LocalDateTime updateAt) {
        this.id = id;
        this.key = key;
        this.value = value;
        this.description = description;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }
}
