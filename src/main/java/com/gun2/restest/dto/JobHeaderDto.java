package com.gun2.restest.dto;

import com.gun2.restest.entity.JobHeader;
import com.gun2.restest.entity.JobParam;
import com.gun2.restest.form.request.JobHeaderRequest;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class JobHeaderDto implements Serializable {
    private Long id;
    private Long jobId;
    private String keyName;
    private String value;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private boolean usable;

    public JobHeaderDto(JobHeader jobHeader){
        this.id = jobHeader.getId();
        this.jobId = jobHeader.getJobId();
        this.keyName = jobHeader.getKeyName();
        this.value = jobHeader.getValue();
        this.description = jobHeader.getDescription();
        this.createdAt = jobHeader.getCreatedAt();
        this.updateAt = jobHeader.getUpdateAt();
        this.usable = jobHeader.isUsable();
    }

    public JobHeaderDto(JobHeaderRequest jobHeaderRequest){
        this.id = jobHeaderRequest.getId();
        this.jobId = jobHeaderRequest.getJobId();
        this.keyName = jobHeaderRequest.getKeyName();
        this.value = jobHeaderRequest.getValue();
        this.description = jobHeaderRequest.getDescription();
        this.usable = jobHeaderRequest.isUsable();
    }

    public JobHeader toEntity(){
        return JobHeader.builder()
                .id(this.id)
                .jobId(this.jobId)
                .keyName(this.keyName)
                .value(this.value)
                .description(this.description)
                .usable(this.usable)
                .build();
    }
}
