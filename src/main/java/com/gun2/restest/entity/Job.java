package com.gun2.restest.entity;

import com.gun2.restest.constant.Method;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @OneToMany(mappedBy = "job",orphanRemoval = true, cascade = CascadeType.ALL)
    private List<JobParam> jobParamList = new ArrayList<>();

    @OneToMany(mappedBy = "job",orphanRemoval = true, cascade = CascadeType.ALL)
    private List<JobHeader> jobHeaderList = new ArrayList<>();

    @OneToMany(mappedBy = "job",orphanRemoval = true, cascade = CascadeType.ALL)
    private List<JobBody> jobBodyList = new ArrayList<>();

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    @Builder
    public Job(Long id,
               String title,
               Method method,
               String url,
               String color,
               List<JobParam> jobParamList,
               List<JobHeader> jobHeaderList,
               List<JobBody> jobBodyList) {
        this.id = id;
        this.title = title;
        this.method = method;
        this.url = url;
        this.color = color;
        this.jobParamList = jobParamList;
        this.jobHeaderList = jobHeaderList;
        this.jobBodyList = jobBodyList;
    }

    public void addJobBody(JobBody jobBody){
        this.jobBodyList.add(jobBody);
        jobBody.updateJob(this);
    }

    public void removeJobBody(JobBody jobBody){
        this.jobBodyList.remove(jobBody);
        jobBody.updateJob(null);
    }

    public void addJobHeader(JobHeader jobHeader){
        this.jobHeaderList.add(jobHeader);
        jobHeader.updateJob(this);
    }

    public void removeJobHeader(JobHeader jobHeader){
        this.jobHeaderList.remove(jobHeader);
        jobHeader.updateJob(null);
    }

    public void addJobParam(JobParam jobParam){
        this.jobParamList.add(jobParam);
        jobParam.updateJob(this);
    }

    public void removeJobParam(JobParam jobParam){
        this.jobParamList.remove(jobParam);
        jobParam.updateJob(null);
    }

}
