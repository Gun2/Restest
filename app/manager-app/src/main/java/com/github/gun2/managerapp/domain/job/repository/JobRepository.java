package com.github.gun2.managerapp.domain.job.repository;

import com.github.gun2.managerapp.domain.job.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}