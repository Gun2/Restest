package com.gun2.restest.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class PerformanceDto {
    private int instance;
    @JsonProperty("jobList")
    private List<JobDto> jobDtoList;
    private String uuid;

    public PerformanceDto(int instance, List<JobDto> jobDtoList, String uuid) {
        this.instance = instance;
        this.jobDtoList = jobDtoList;
        this.uuid = uuid;
    }
}
