package com.gun2.restest.service.impl;

import com.gun2.restest.dto.JobDto;
import com.gun2.restest.entity.Job;
import com.gun2.restest.exception.IdentityIsNullException;
import com.gun2.restest.exception.RowNotFoundByIdException;
import com.gun2.restest.repository.JobRepository;
import com.gun2.restest.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    @Override
    @Transactional
    public List<JobDto> findAll() {
        return jobRepository.findAll().stream().map(JobDto::new).toList();
    }

    @Override
    @Transactional
    public JobDto findById(Long id) {
        if(ObjectUtils.isEmpty(id)){
            throw new IdentityIsNullException("요청으로부터 id값을 받지 못했습니다.");
        }
        Optional<Job> optionalJob = jobRepository.findById(id);
        Job job = optionalJob.orElseGet( () -> {
            throw new RowNotFoundByIdException("entity를 찾지 못했습니다.", id);
        });
        return new JobDto(job);
    }

    @Override
    public JobDto insert(JobDto jobDto) {
        Job result = jobRepository.save(jobDto.toEntity());
        return new JobDto(result);
    }

    @Override
    public JobDto update(JobDto jobDto) {
        Optional<Job> target = jobRepository.findById(jobDto.getId());
        target.orElseGet(() -> {
            throw new RowNotFoundByIdException("update할 entity를 찾지 못했습니다.", jobDto.getId());
        });
        return this.insert(jobDto);
    }

    @Override
    public void delete(long id) {
        jobRepository.deleteById(id);
    }
}

