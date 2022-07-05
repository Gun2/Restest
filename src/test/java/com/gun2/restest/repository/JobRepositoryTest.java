package com.gun2.restest.repository;

import com.gun2.restest.constant.Method;
import com.gun2.restest.entity.Job;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@DataJpaTest
public class JobRepositoryTest {

    @Autowired
    private JobRepository jobRepository;

    @Nested
    @DisplayName("CRUD 테스트")
    class Crud{

        @Test
        @DisplayName("생성, 읽기, 수정, 삭제 테스트")
        void test01(){
            Job job = Job.builder().method(Method.GET).build();
            jobRepository.save(job);

            assertThat(job.getId()).as("Insert 후 부여받은 ID 확인")
                    .isNotNull();

            log.info("created id : {}", job.getId());
            long id = job.getId();

            Job updateJob = Job.builder().id(job.getId()).title("무야호").build();
            jobRepository.save(updateJob);

            assertThat(job.getTitle()).as("타이틀 수정 확인").isEqualTo("무야호");
            log.info("updated title : {}", job.getTitle());

            jobRepository.delete(updateJob);

            Optional<Job> findJob = jobRepository.findById(id);

            Throwable thrown = catchThrowable(() -> {
                findJob.orElseGet(() -> {
                    throw new EntityNotFoundException("해당 row 를 찾을 수 없습니다.");
                });
            });
            assertThat(thrown)
                    .as("entity 삭제 확인")
                    .isInstanceOf(EntityNotFoundException.class);

        }

    }
}
