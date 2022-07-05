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
    private String keyName;
    private String value;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public JobParamDto(JobParam jobParam){
        this.id = jobParam.getId();
        this.keyName = jobParam.getKeyName();
        this.value = jobParam.getValue();
        this.description = jobParam.getDescription();
        this.createdAt = jobParam.getCreatedAt();
        this.updateAt = jobParam.getUpdateAt();
    }

    public JobParam toEntity(){
        return JobParam.builder()
                .id(this.id)
                .keyName(this.keyName)
                .value(this.value)
                .description(this.description)
                .build();
    }
}
