package com.github.gun2.managerapp.domain.schedule.service.impl;

import com.github.gun2.managerapp.domain.schedule.service.ScheduleJobService;
import com.github.gun2.managerapp.exception.RowNotFoundFromIdException;
import com.github.gun2.managerapp.domain.job.repository.JobRepository;
import com.github.gun2.managerapp.domain.schedule.repository.ScheduleJobRepository;
import com.github.gun2.managerapp.domain.schedule.dto.ScheduleJobDto;
import com.github.gun2.managerapp.domain.job.entity.Job;
import com.github.gun2.managerapp.domain.schedule.entity.Schedule;
import com.github.gun2.managerapp.domain.schedule.entity.ScheduleJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {

    private final ScheduleJobRepository scheduleJobRepository;
    private final JobRepository jobRepository;

    @Override
    public List<ScheduleJobDto> insertAll(Schedule schedule, List<Long> jobIdList) {
        AtomicInteger cnt = new AtomicInteger();
        List<ScheduleJob> scheduleJobList = jobIdList.stream().map(jobId -> {
            Job job = jobRepository.findById(jobId).orElseGet(() -> {
                throw new RowNotFoundFromIdException("job을 찾지 못했습니다.", jobId);
            });
            return ScheduleJob.builder()
                    .job(job)
                    .schedule(schedule)
                    .sort(cnt.getAndIncrement())
                    .build();
        }).toList();
        scheduleJobRepository.saveAll(scheduleJobList);
        return scheduleJobList.stream().map(ScheduleJobDto::new).toList();
    }

    @Override
    public void deleteByScheduleId(Long scheduleId) {
        scheduleJobRepository.deleteAllByScheduleId(scheduleId);
    }


}
