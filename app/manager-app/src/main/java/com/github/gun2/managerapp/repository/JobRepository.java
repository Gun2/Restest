package com.github.gun2.managerapp.repository;

import com.github.gun2.managerapp.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}