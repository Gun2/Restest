package com.gun2.restest.dto;

import com.gun2.restest.constant.Method;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class JobDto implements Serializable {
    private final Long id;
    private final String title;
    private final Method method;
    @URL
    private final String url;
    private final List<ParamDto> paramEntityList;
    private final List<HeaderDto> headerEntityList;
    private final List<BodyDto> bodyEntities;
    private final LocalDateTime createdAt;
    private final LocalDateTime updateAt;
}
