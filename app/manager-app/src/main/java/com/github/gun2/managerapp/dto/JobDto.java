package com.github.gun2.managerapp.dto;

import com.github.gun2.managerapp.constant.Method;
import com.github.gun2.managerapp.entity.Job;
import com.github.gun2.managerapp.form.request.JobRequest;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class JobDto implements Serializable {
    private Long id;
    private String title;
    private Method method;
    private String url;
    private String color;
    private List<JobParamDto> jobParamList = new ArrayList<>();
    private List<JobHeaderDto> jobHeaderList = new ArrayList<>();
    private List<JobBodyDto> jobBodyList = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public JobDto(Job job){
        this.id = job.getId();
        this.title = job.getTitle();
        this.method = job.getMethod();
        this.url = job.getUrl();
        this.color = job.getColor();
        this.jobParamList = job.getJobParamList().stream().map(JobParamDto::new).toList();
        this.jobHeaderList = job.getJobHeaderList().stream().map(JobHeaderDto::new).toList();
        this.jobBodyList = job.getJobBodyList().stream().map(JobBodyDto::new).toList();
        this.createdAt = job.getCreatedAt();
        this.updateAt = job.getUpdateAt();
    }

    public JobDto(JobRequest jobRequest){
        this.id = jobRequest.getId();
        this.title = jobRequest.getTitle();
        this.method = jobRequest.getMethod();
        this.url = jobRequest.getUrl();
        this.color = jobRequest.getColor();
        this.jobParamList = jobRequest.getJobParamList().stream().map(JobParamDto::new).toList();
        this.jobHeaderList = jobRequest.getJobHeaderList().stream().map(JobHeaderDto::new).toList();
        this.jobBodyList = jobRequest.getJobBodyList().stream().map(JobBodyDto::new).toList();
    }

    public Job toEntity(){
        Job job = Job.builder()
                .id(this.id)
                .title(this.title)
                .method(this.method)
                .url(this.url)
                .color(this.color)
                .jobParamList(this.jobParamList.stream().map(dto -> dto.toEntity()).toList())
                .jobHeaderList(this.jobHeaderList.stream().map(dto -> dto.toEntity()).toList())
                .jobBodyList(this.jobBodyList.stream().map(dto -> dto.toEntity()).toList())
                .build();
        job.getJobBodyList().forEach(jobBody -> {jobBody.updateJob(job);});
        job.getJobParamList().forEach(jobParam -> {jobParam.updateJob(job);});
        job.getJobHeaderList().forEach(jobHeader -> {jobHeader.updateJob(job);});
        return job;
    }

}
