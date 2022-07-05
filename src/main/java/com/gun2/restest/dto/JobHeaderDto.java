package com.gun2.restest.dto;

import com.gun2.restest.entity.JobHeader;
import com.gun2.restest.entity.JobParam;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class JobHeaderDto implements Serializable {
    private Long id;
    private String keyName;
    private String value;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public JobHeaderDto(JobHeader jobHeader){
        this.id = jobHeader.getId();
        this.keyName = jobHeader.getKeyName();
        this.value = jobHeader.getValue();
        this.description = jobHeader.getDescription();
        this.createdAt = jobHeader.getCreatedAt();
        this.updateAt = jobHeader.getUpdateAt();
    }

    public JobHeader toEntity(){
        return JobHeader.builder()
                .id(this.id)
                .keyName(this.keyName)
                .value(this.value)
                .description(this.description)
                .build();
    }
}
