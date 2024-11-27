package com.github.gun2.managerapp.repository;

import com.github.gun2.managerapp.config.QueryDslConfig;
import com.github.gun2.managerapp.entity.Job;
import com.github.gun2.managerapp.entity.JobBody;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Slf4j
@Import(QueryDslConfig.class)
class JobBodyRepositoryTest {

    @Autowired
    JobBodyRepository jobBodyRepository;
    @Autowired
    JobRepository jobRepository;

    @Test
    @DisplayName("생성 삭제 테스트")
    void test01() {
        /** given */
        Job job = jobRepository.save(Job.builder().build());
        JobBody save = jobBodyRepository.save(JobBody.builder().job(job).build());

        /** when */
        jobBodyRepository.delete(save);
        Optional<JobBody> byId = jobBodyRepository.findById(save.getId());

        /** then */
        assertThat(byId.isEmpty()).isTrue();
    }
}