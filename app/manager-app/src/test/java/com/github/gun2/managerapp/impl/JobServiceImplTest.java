package com.github.gun2.managerapp.impl;


import com.github.gun2.managerapp.constant.Method;
import com.github.gun2.managerapp.domain.job.dto.JobDto;
import com.github.gun2.managerapp.exception.RowNotFoundFromIdException;
import com.github.gun2.managerapp.domain.job.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ComponentScan
@DataJpaTest
public class JobServiceImplTest {

    @Autowired
    private JobService jobService;

    @Nested
    @DisplayName("기본 CRUD 테스트")
    class Crud{


       JobDto create() {
           JobDto insertDto = new JobDto();
           insertDto.setTitle("무야호");
           insertDto.setMethod(Method.POST);
           JobDto insertResult = jobService.insert(insertDto);
           assertThat(insertResult.getId()).as("값 insert 후 id값을 부여받아야함.")
                   .isNotNull();
           return insertResult;
       }

       @Test
       @DisplayName("read 테스트")
       void read(){
           JobDto insertResult = create();
           JobDto selectResult = jobService.findById(insertResult.getId());
           assertThat(selectResult.getTitle()).as("select 데이터를 올바르게 가져와야함")
                   .isEqualTo(insertResult.getTitle());
       }

       @Test
       @DisplayName("update 테스트")
       void update(){
           JobDto insertResult = create();
           JobDto updateDto = new JobDto();
           updateDto.setId(insertResult.getId());
           updateDto.setTitle("그만큼 신나시다는거지");
           JobDto updateResult = jobService.update(updateDto);
           assertThat(updateResult.getTitle()).as("변경 내용 확인")
                   .isEqualTo("그만큼 신나시다는거지");
       }

       @Test
       @DisplayName("delete 테스트")
       void delete(){
           JobDto insertResult = create();
           jobService.delete(insertResult.getId());
           Throwable throwable = catchThrowable(() -> {
               jobService.findById(insertResult.getId());
           });
           assertThat(throwable).as("row를 찾을 수 없어야함.")
                   .isInstanceOf(RowNotFoundFromIdException.class);
       }

    }
}
