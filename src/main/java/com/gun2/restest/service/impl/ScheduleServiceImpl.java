package com.gun2.restest.service.impl;

import com.gun2.restest.component.scheduler.SchedulerComponent;
import com.gun2.restest.dto.ScheduleDto;
import com.gun2.restest.dto.ScheduleJobDto;
import com.gun2.restest.dto.ScheduleRunDto;
import com.gun2.restest.entity.Schedule;
import com.gun2.restest.exception.IdentityIsNullException;
import com.gun2.restest.exception.RowNotFoundFromIdException;
import com.gun2.restest.repository.ScheduleRepository;
import com.gun2.restest.service.ScheduleJobService;
import com.gun2.restest.service.ScheduleService;
import lombok.RequiredArgsConstructor;
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
        scheduleRepository.deleteById(id);
        scheduleJobService.deleteByScheduleId(id);
        componentDelete(id);
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
