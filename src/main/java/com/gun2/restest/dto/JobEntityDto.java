package com.gun2.restest.dto;

import com.gun2.restest.constant.Method;
import com.gun2.restest.entity.JobEntity;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class JobEntityDto implements Serializable {
    private final Long id;
    private final String title;
    private final Method method;
    @URL
    private final String url;
    private final List<ParamEntityDto> paramEntityList;
    private final List<HeaderEntityDto> headerEntityList;
    private final List<BodyEntityDto> bodyEntities;
    private final LocalDateTime createdAt;
    private final LocalDateTime updateAt;
}
