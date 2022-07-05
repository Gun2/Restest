package com.gun2.restest.dto;

import com.gun2.restest.entity.JobBody;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class JobBodyDto implements Serializable {
    private Long id;
    private String body;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public JobBodyDto(JobBody jobBody){
        this.id = jobBody.getId();
        this.body = jobBody.getBody();
        this.createdAt = jobBody.getCreatedAt();
        this.updateAt = jobBody.getUpdateAt();
    }

    public JobBody toEntity(){
        return JobBody.builder()
                .id(this.id)
                .body(this.body)
                .build();
    }
}
