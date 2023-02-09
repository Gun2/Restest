package com.gun2.restest.repository;

import com.gun2.restest.config.QueryDslConfig;
import com.gun2.restest.entity.Job;
import com.gun2.restest.entity.JobBody;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

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