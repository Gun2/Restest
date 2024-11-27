package com.github.gun2.managerapp.repository;

import com.github.gun2.managerapp.entity.Schedule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class ScheduleRepositoryTest {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Test
    @DisplayName("동작 schedule 리스트 가져오기")
    void findByRun(){
        Schedule testSchedule = Schedule.builder().run(true).build();
        scheduleRepository.save(testSchedule);
        List<Schedule> runScheduleList = scheduleRepository.findByRun(true);

        assertThat(runScheduleList.stream().filter(r -> !r.isRun()).toList().size()).as("run이 false인 값이 없어야함")
                .isEqualTo(0);

        runScheduleList.forEach( r -> {
            log.info("id : {}, run : {}",r.getId(), r.isRun());
        });

    }
}
