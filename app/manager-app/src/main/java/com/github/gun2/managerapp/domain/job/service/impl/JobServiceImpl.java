package com.github.gun2.managerapp.domain.job.service.impl;

import com.github.gun2.managerapp.domain.job.event.JobDataChangeEvent;
import com.github.gun2.managerapp.domain.scheduler.component.SchedulerComponent;
import com.github.gun2.managerapp.domain.job.service.JobService;
import com.github.gun2.managerapp.event.DataChangeEvent;
import com.github.gun2.managerapp.exception.IdentityIsNullException;
import com.github.gun2.managerapp.exception.RowNotFoundFromIdException;
import com.github.gun2.managerapp.domain.job.repository.JobBodyRepository;
import com.github.gun2.managerapp.domain.job.repository.JobHeaderRepository;
import com.github.gun2.managerapp.domain.job.repository.JobRepository;
import com.github.gun2.managerapp.domain.schedule.repository.ScheduleJobRepository;
import com.github.gun2.managerapp.domain.job.dto.JobDto;
import com.github.gun2.managerapp.domain.job.entity.Job;
import com.github.gun2.managerapp.util.ColorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationEventPublisher;
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
    private final SchedulerComponent schedulerComponent;
    private final ApplicationEventPublisher applicationEventPublisher;

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
        setColorOfJob(jobDto);
        Job job = jobDto.toEntity();
        Job result = jobRepository.save(job);
        JobDto insertedDto = new JobDto(result);
        if (jobDto.getId() == null){
            applicationEventPublisher.publishEvent(new JobDataChangeEvent(insertedDto, DataChangeEvent.Type.CREATE));
        }
        return insertedDto;
    }

    /**
     * <p>job에 색상 설정이 없는 경우 생상 부여</p>
     * @param jobDto
     */
    private void setColorOfJob(JobDto jobDto) {
        if(StringUtils.isBlank(jobDto.getColor())){
            jobDto.setColor(new ColorUtil().generateLightHexColor());
        }
    }

    @Override
    @Transactional
    public JobDto update(JobDto jobDto) {
        Optional<Job> target = jobRepository.findById(jobDto.getId());
        target.orElseGet(() -> {
            throw new RowNotFoundFromIdException("update할 entity를 찾지 못했습니다.", jobDto.getId());
        });
        JobDto insertResult =  this.insert(jobDto);
        updateComponent(jobDto);
        applicationEventPublisher.publishEvent(new JobDataChangeEvent(insertResult, DataChangeEvent.Type.UPDATE));
        return insertResult;
    }

    @Override
    @Transactional
    public void delete(long id) {
        scheduleJobRepository.deleteAllByJobId(id);
        jobHeaderRepository.deleteAllByJobId(id);
        jobBodyRepository.deleteAllByJobId(id);
        jobRepository.deleteById(id);
        deleteComponent(id);
        applicationEventPublisher.publishEvent(
                new JobDataChangeEvent(JobDto.builder().id(id).build(), DataChangeEvent.Type.DELETE)
        );

    }

    /**
     * TODO : 트랜젝션에 따른 롤백 방안 필요.
     * <b>업데이트된 업무와 관련된 스케줄러를 업데이트한다.</b>
     * @param jobDto
     */
    public void updateComponent(JobDto jobDto){
        schedulerComponent.updateJob(jobDto);
    }

    /**
     * <b>job 삭제 시 구동중인 스케줄러에서도 삭제</b>
     * @param id 삭제할 job id
     */
    public void deleteComponent(Long id){
        schedulerComponent.deleteJob(id);
    }
}

