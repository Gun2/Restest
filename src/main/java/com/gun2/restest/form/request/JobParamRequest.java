package com.gun2.restest.form.request;

import com.gun2.restest.entity.JobParam;
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
public class JobParamRequest implements Serializable {
    private Long id;
    private Long jobId;
    private String keyName;
    private String value;
    private String description;
    private boolean usable;
}
