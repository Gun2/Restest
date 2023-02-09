package com.gun2.restest.service.impl;

import com.gun2.restest.dto.ScheduleJobDto;
import com.gun2.restest.entity.Job;
import com.gun2.restest.entity.Schedule;
import com.gun2.restest.entity.ScheduleJob;
import com.gun2.restest.exception.RowNotFoundFromIdException;
import com.gun2.restest.repository.JobRepository;
import com.gun2.restest.repository.ScheduleJobRepository;
import com.gun2.restest.service.ScheduleJobService;
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
