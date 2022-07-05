package com.gun2.restest.service;

import com.gun2.restest.dto.JobDto;

import java.util.List;

public interface JobService {
    List<JobDto> findAll();
}
