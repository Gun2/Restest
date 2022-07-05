package com.gun2.restest.dto;

import com.gun2.restest.constant.Method;
import com.gun2.restest.entity.Job;
import com.gun2.restest.entity.JobHeader;
import lombok.*;
import org.hibernate.validator.constraints.URL;

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
    @URL
    private String url;
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
        this.jobParamList = job.getJobParamList().stream().map(JobParamDto::new).toList();
        this.jobHeaderList = job.getJobHeaderList().stream().map(JobHeaderDto::new).toList();
        this.jobBodyList = job.getJobBodyList().stream().map(JobBodyDto::new).toList();
        this.createdAt = job.getCreatedAt();
        this.updateAt = job.getUpdateAt();
    }

    public Job toEntity(){
        return Job.builder()
                .id(this.id)
                .title(this.title)
                .method(this.method)
                .url(this.url)
                .jobParamList(this.jobParamList.stream().map(dto -> dto.toEntity()).toList())
                .jobHeaderList(this.jobHeaderList.stream().map(dto -> dto.toEntity()).toList())
                .jobBodyList(this.jobBodyList.stream().map(dto -> dto.toEntity()).toList())
                .build();
    }

}
