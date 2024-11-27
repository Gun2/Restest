package com.github.gun2.managerapp.repository;

import com.github.gun2.managerapp.repository.custom.JobBodyCustomRepository;
import com.github.gun2.managerapp.entity.JobBody;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobBodyRepository extends JpaRepository<JobBody, Long>, JobBodyCustomRepository {
}