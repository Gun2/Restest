package com.gun2.restest.repository;

import com.gun2.restest.entity.JobBody;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BodyRepository extends JpaRepository<JobBody, Long> {
}