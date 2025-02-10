package com.github.gun2.managerapp.domain.job.repository;

import com.github.gun2.managerapp.domain.job.repository.custom.JobHeaderCustomRepository;
import com.github.gun2.managerapp.domain.job.entity.JobHeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobHeaderRepository extends JpaRepository<JobHeader, Long>, JobHeaderCustomRepository {
}