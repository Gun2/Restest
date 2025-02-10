package com.github.gun2.managerapp.domain.job.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class JobParamRequest implements Serializable {
    private Long id;
    private Long jobId;
    private String keyName;
    private String value;
    private String description;
    private boolean usable;
}
