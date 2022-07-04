package com.gun2.restest.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class BodyDto implements Serializable {
    private final Long id;
    private final String body;
    private final LocalDateTime createdAt;
    private final LocalDateTime updateAt;
}
