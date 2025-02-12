package com.github.gun2.managerapp.domain.schedule.service.impl;

import com.github.gun2.managerapp.domain.schedule.event.ScheduleDataChangeEvent;
import com.github.gun2.managerapp.domain.scheduler.component.SchedulerComponent;
import com.github.gun2.managerapp.domain.schedule.service.ScheduleJobService;
import com.github.gun2.managerapp.domain.schedule.service.ScheduleService;
import com.github.gun2.managerapp.event.DataChangeEvent;
import com.github.gun2.managerapp.exception.IdentityIsNullException;
import com.github.gun2.managerapp.exception.RowNotFoundFromIdException;
import com.github.gun2.managerapp.domain.schedule.repository.ScheduleRepository;
import com.github.gun2.managerapp.domain.schedule.dto.ScheduleDto;
import com.github.gun2.managerapp.domain.schedule.dto.ScheduleJobDto;
import com.github.gun2.managerapp.domain.scheduler.dto.ScheduleRunDto;
import com.github.gun2.managerapp.domain.schedule.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleJobService scheduleJobService;
    private final SchedulerComponent schedulerComponent;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public List<ScheduleDto> findAll() {
        return scheduleRepository.findAll(Sort.by(Sort.Direction.DESC,"id")).stream().map(ScheduleDto::new).toList();
    }

    @Override
    @Transactional
    public ScheduleDto findById(Long id) {
        if(ObjectUtils.isEmpty(id)){
            throw new IdentityIsNullException("요청으로부터 id값을 받지 못했습니다.");
        }
        Optional<Schedule> optionalSchedule = scheduleRepository.findById(id);
        Schedule Schedule = optionalSchedule.orElseGet( () -> {
            throw new RowNotFoundFromIdException("entity를 찾지 못했습니다.", id);
        });
        return new ScheduleDto(Schedule);
    }

    @Override
    @Transactional
    public ScheduleDto insert(ScheduleDto scheduleDto) {
        Schedule result = scheduleRepository.save(scheduleDto.toEntity());
        List<ScheduleJobDto> scheduleJobDtoList = scheduleJobService.insertAll(result, scheduleDto.getJobIdList());
        ScheduleDto resultDto = new ScheduleDto(result);
        resultDto.setJobIdList(scheduleJobDtoList.stream().map(s -> s.getJobDto().getId()).toList());
        resultDto.setJobList(scheduleJobDtoList.stream().map(s -> s.getJobDto()).toList());
        if (scheduleDto.getId() == null){
            applicationEventPublisher.publishEvent(new ScheduleDataChangeEvent(resultDto, DataChangeEvent.Type.CREATE));
        }
        return resultDto;
    }

    @Override
    @Transactional
    public ScheduleDto update(ScheduleDto scheduleDto) {
        Optional<Schedule> target = scheduleRepository.findById(scheduleDto.getId());
        target.orElseGet(() -> {
            throw new RowNotFoundFromIdException("update할 entity를 찾지 못했습니다.", scheduleDto.getId());
        });
        scheduleJobService.deleteByScheduleId(scheduleDto.getId());
        ScheduleDto insertResult = this.insert(scheduleDto);
        componentUpdate(insertResult);
        applicationEventPublisher.publishEvent(new ScheduleDataChangeEvent(insertResult, DataChangeEvent.Type.UPDATE));
        return insertResult;
    }

    /**
     * <b>component 정보 업데이트</b>
     * @param scheduleDto update된 컴포넌트 정보
     */
    public void componentUpdate(ScheduleDto scheduleDto){
        schedulerComponent.updateSchedule(scheduleDto);
    }

    /**
     * <b>compoenet 정보 삭제</b>
     * @param scheduleId 삭제된 scheduleId
     */
    public void componentDelete(Long scheduleId){
        schedulerComponent.deleteSchedule(scheduleId);
    }


    @Override
    @Transactional
    public void delete(long id) {
        scheduleRepository.findById(id).ifPresent(schedule -> {
            ScheduleDto origin = new ScheduleDto(schedule);
            scheduleRepository.deleteById(id);
            scheduleJobService.deleteByScheduleId(id);
            componentDelete(id);
            applicationEventPublisher.publishEvent(new ScheduleDataChangeEvent(origin, DataChangeEvent.Type.DELETE));
        });
    }

    @Override
    @Transactional
    public void updateRun(ScheduleRunDto scheduleRunDto) {
        scheduleRepository.updateRun(scheduleRunDto);
        if(scheduleRunDto.getRun()){
            //동작이면 컴폰넌트에 정보 추가
            ScheduleDto scheduleDto = this.findById(scheduleRunDto.getId());
            componentUpdate(scheduleDto);
        }else{
            //스톱이면 컴포넌트에 정보 삭제
            componentDelete(scheduleRunDto.getId());
        }
    }

    @Override
    @Transactional
    public List<ScheduleDto> findByRun(boolean run) {
        return scheduleRepository.findByRun(run).stream().map(ScheduleDto::new).toList();
    }
}
