package com.github.gun2.managerapp.form.request;

import lombok.Getter;

import jakarta.validation.constraints.NotBlank;

@Getter
public class PerformanceRequest {

    @NotBlank
    private String uuid;

}
