package com.github.gun2.managerapp.domain.job.repository;

import com.github.gun2.managerapp.domain.job.entity.JobParam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobParamRepository extends JpaRepository<JobParam, Long> {
}