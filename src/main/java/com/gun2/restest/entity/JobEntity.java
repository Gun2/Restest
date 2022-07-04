package com.gun2.restest.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private Method method;

    @URL
    private String url;

    @OneToMany
    private List<ParamEntity> paramEntityList = new ArrayList<>();

    @OneToMany
    private List<HeaderEntity> headerEntityList = new ArrayList<>();



    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @CreationTimestamp
    private LocalDateTime updateAt;

    enum Method{
        GET,
        POST,
        UPDATE,
        DELETE
    }

    @Builder
    public JobEntity(Long id, String title, Method method, String url, LocalDateTime createdAt, LocalDateTime updateAt) {
        this.id = id;
        this.title = title;
        this.method = method;
        this.url = url;
        this.createdAt = createdAt;
        this.updateAt = updateAt;
    }
}
