package com.github.gun2.managerapp.dto;

import com.github.gun2.managerapp.entity.JobParam;
import com.github.gun2.managerapp.form.request.JobParamRequest;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class JobParamDto implements Serializable {
    private Long id;
    private String keyName;
    private String value;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
    private boolean usable;

    public JobParamDto(JobParam jobParam){
        this.id = jobParam.getId();
        this.keyName = jobParam.getKeyName();
        this.value = jobParam.getValue();
        this.description = jobParam.getDescription();
        this.createdAt = jobParam.getCreatedAt();
        this.updateAt = jobParam.getUpdateAt();
        this.usable = jobParam.isUsable();
    }

    public JobParamDto(JobParamRequest jobParamRequest){
        this.id = jobParamRequest.getId();
        this.keyName = jobParamRequest.getKeyName();
        this.value = jobParamRequest.getValue();
        this.description = jobParamRequest.getDescription();
        this.usable = jobParamRequest.isUsable();
    }

    public JobParam toEntity(){
        return JobParam.builder()
                .id(this.id)
                .keyName(this.keyName)
                .value(this.value)
                .description(this.description)
                .usable(this.usable)
                .build();
    }
}
