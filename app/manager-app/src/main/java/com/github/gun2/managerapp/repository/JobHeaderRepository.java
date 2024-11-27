package com.github.gun2.managerapp.repository;

import com.github.gun2.managerapp.repository.custom.JobHeaderCustomRepository;
import com.github.gun2.managerapp.entity.JobHeader;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobHeaderRepository extends JpaRepository<JobHeader, Long>, JobHeaderCustomRepository {
}