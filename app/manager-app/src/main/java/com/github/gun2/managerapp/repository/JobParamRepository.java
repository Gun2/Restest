package com.github.gun2.managerapp.repository;

import com.github.gun2.managerapp.entity.JobParam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobParamRepository extends JpaRepository<JobParam, Long> {
}