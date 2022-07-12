package com.gun2.restest.dto;

import com.gun2.restest.entity.JobParam;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class JobParamDto implements Serializable {
    private Long id;
    private Long jobId;
    private String keyName;
    private String value;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private boolean usable;

    public JobParamDto(JobParam jobParam){
        this.id = jobParam.getId();
        this.jobId = jobParam.getJobId();
        this.keyName = jobParam.getKeyName();
        this.value = jobParam.getValue();
        this.description = jobParam.getDescription();
        this.createdAt = jobParam.getCreatedAt();
        this.updateAt = jobParam.getUpdateAt();
        this.usable = jobParam.isUsable();
    }

    public JobParam toEntity(){
        return JobParam.builder()
                .id(this.id)
                .jobId(this.jobId)
                .keyName(this.keyName)
                .value(this.value)
                .description(this.description)
                .usable(this.usable)
                .build();
    }
}
