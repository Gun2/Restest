package com.gun2.restest.repository;

import com.gun2.restest.entity.JobHeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobHeaderRepository extends JpaRepository<JobHeader, Long> {
}