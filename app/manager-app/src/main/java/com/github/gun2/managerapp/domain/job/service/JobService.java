package com.github.gun2.managerapp.domain.job.service;

import com.github.gun2.managerapp.domain.job.dto.JobDto;

import java.util.List;

public interface JobService {
    List<JobDto> findAll();

    JobDto findById(Long id);

    JobDto insert(JobDto jobDto);

    JobDto update(JobDto jobDto);

    void delete(long id);
}
