package com.gun2.restest.repository;

import com.gun2.restest.entity.JobBody;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobBodyRepository extends JpaRepository<JobBody, Long> {
}