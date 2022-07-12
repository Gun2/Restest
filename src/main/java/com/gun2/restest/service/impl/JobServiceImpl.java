package com.gun2.restest.service.impl;

import com.gun2.restest.dto.JobDto;
import com.gun2.restest.entity.Job;
import com.gun2.restest.exception.IdentityIsNullException;
import com.gun2.restest.exception.RowNotFoundFromIdException;
import com.gun2.restest.repository.JobBodyRepository;
import com.gun2.restest.repository.JobHeaderRepository;
import com.gun2.restest.repository.JobRepository;
import com.gun2.restest.repository.ScheduleJobRepository;
import com.gun2.restest.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final JobHeaderRepository jobHeaderRepository;
    private final JobBodyRepository jobBodyRepository;
    private final ScheduleJobRepository scheduleJobRepository;

    @Override
    @Transactional
    public List<JobDto> findAll() {
        return jobRepository.findAll(Sort.by(Sort.Direction.DESC,"id")).stream().map(JobDto::new).toList();
    }

    @Override
    @Transactional
    public JobDto findById(Long id) {
        if(ObjectUtils.isEmpty(id)){
            throw new IdentityIsNullException("요청으로부터 id값을 받지 못했습니다.");
        }
        Optional<Job> optionalJob = jobRepository.findById(id);
        Job job = optionalJob.orElseGet( () -> {
            throw new RowNotFoundFromIdException("entity를 찾지 못했습니다.", id);
        });
        return new JobDto(job);
    }

    @Override
    @Transactional
    public JobDto insert(JobDto jobDto) {
        Job job = jobDto.toEntity();
        jobHeaderRepository.saveAll(job.getJobHeaderList());
        jobBodyRepository.saveAll(job.getJobBodyList());
        Job result = jobRepository.save(job);
        return new JobDto(result);
    }

    @Override
    public JobDto update(JobDto jobDto) {
        Optional<Job> target = jobRepository.findById(jobDto.getId());
        target.orElseGet(() -> {
            throw new RowNotFoundFromIdException("update할 entity를 찾지 못했습니다.", jobDto.getId());
        });
        return this.insert(jobDto);
    }

    @Override
    @Transactional
    public void delete(long id) {
        scheduleJobRepository.deleteAllByJobId(id);
        jobHeaderRepository.deleteAllByJobId(id);
        jobBodyRepository.deleteAllByJobId(id);
        jobRepository.deleteById(id);
    }
}

