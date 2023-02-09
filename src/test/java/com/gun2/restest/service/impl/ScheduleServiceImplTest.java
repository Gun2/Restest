package com.gun2.restest.service.impl;

import static org.assertj.core.api.Assertions.*;

import com.gun2.restest.dto.ScheduleDto;
import com.gun2.restest.entity.Job;
import com.gun2.restest.entity.Schedule;
import com.gun2.restest.entity.ScheduleJob;
import com.gun2.restest.repository.JobRepository;
import com.gun2.restest.repository.ScheduleJobRepository;
import com.gun2.restest.repository.ScheduleRepository;
import com.gun2.restest.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@DataJpaTest
public class ScheduleServiceImplTest {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ScheduleJobRepository scheduleJobRepository;

    @Nested
    @DisplayName("CRUD 테스트")
    class Crud{

        @Test
        @DisplayName("기본 insert 테스트")
        void insert(){
            List<Long> jobIdList = new ArrayList<>();
            for(int i = 1; i <=10; i ++){
                long id = (long)i;
                jobIdList.add(id);
                Job job = Job.builder()
                        .id(id)
                        .title("test"+i)
                        .build();
                jobRepository.save(job);
            }
            ScheduleDto scheduleDto = new ScheduleDto();
            scheduleDto.setTitle("스케줄1");
            scheduleDto.setDelay(1000);
            scheduleDto.setJobIdList(jobIdList);

            Schedule schedule = scheduleDto.toEntity();
            List<ScheduleJob> scheduleJobList = scheduleDto.getJobIdList().stream().map(jobId -> {
               Job job = jobRepository.findById(jobId).orElseGet(() -> {
                   throw new IllegalArgumentException("job이 없음");
               });
               return ScheduleJob.builder()
                       .schedule(schedule)
                       .job(job)
                       .build();
            }).toList();
            scheduleRepository.save(schedule);
            scheduleJobRepository.saveAll(scheduleJobList);
            log.info("schedule===");
            scheduleRepository.findAll().forEach(s -> {
                log.info(s.getTitle());
            });
            log.info("scheduleJob===");
            scheduleJobRepository.findAll().forEach(s -> {
                log.info(s.getSchedule().getTitle());
            });
        }
    }
}
