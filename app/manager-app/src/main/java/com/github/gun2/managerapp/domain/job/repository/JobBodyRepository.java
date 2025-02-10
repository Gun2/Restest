package com.github.gun2.managerapp.domain.job.repository;

import com.github.gun2.managerapp.domain.job.repository.custom.JobBodyCustomRepository;
import com.github.gun2.managerapp.domain.job.entity.JobBody;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobBodyRepository extends JpaRepository<JobBody, Long>, JobBodyCustomRepository {
}