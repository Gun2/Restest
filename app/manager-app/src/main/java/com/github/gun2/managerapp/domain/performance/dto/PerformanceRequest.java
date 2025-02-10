package com.github.gun2.managerapp.domain.performance.dto;

import lombok.Getter;

import jakarta.validation.constraints.NotBlank;

@Getter
public class PerformanceRequest {

    @NotBlank
    private String uuid;

}
