package com.gun2.restest.entity;

import com.gun2.restest.constant.Method;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
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
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private Method method;
    private String url;
    @Column(name = "color", length = 7)
    private String color;

    @OneToMany
    @JoinColumn(name = "jobId")
    private List<JobParam> jobParamList = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "jobId")
    private List<JobHeader> jobHeaderList = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "jobId")
    private List<JobBody> jobBodyList = new ArrayList<>();

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    @Builder
    public Job(Long id, String title, Method method, String url, String color, List<JobParam> jobParamList, List<JobHeader> jobHeaderList, List<JobBody> jobBodyList) {
        this.id = id;
        this.title = title;
        this.method = method;
        this.url = url;
        this.color = color;
        this.jobParamList = jobParamList;
        this.jobHeaderList = jobHeaderList;
        this.jobBodyList = jobBodyList;
    }
}
