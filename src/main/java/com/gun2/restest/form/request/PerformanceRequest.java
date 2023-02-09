package com.gun2.restest.form.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class PerformanceRequest {

    @NotBlank
    private String uuid;

}
