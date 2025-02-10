package com.github.gun2.managerapp.domain.job.dto;

import com.github.gun2.managerapp.domain.job.entity.JobBody;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class JobBodyDto implements Serializable {
    private Long id;
    private String body;
    private Integer afterDelay;

    private boolean usable;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public JobBodyDto(JobBody jobBody){
        this.id = jobBody.getId();
        this.body = jobBody.getBody();
        this.afterDelay = jobBody.getAfterDelay();
        this.createdAt = jobBody.getCreatedAt();
        this.updateAt = jobBody.getUpdateAt();
        this.usable = jobBody.isUsable();
    }

    public JobBodyDto(JobBodyRequest jobBodyRequest){
        this.id = jobBodyRequest.getId();
        this.body = jobBodyRequest.getBody();
        this.afterDelay = jobBodyRequest.getAfterDelay();
        this.usable = jobBodyRequest.isUsable();
    }

    public JobBody toEntity(){
        return JobBody.builder()
                .id(this.id)
                .body(this.body)
                .afterDelay(this.afterDelay)
                .usable(this.usable)
                .build();
    }
}
