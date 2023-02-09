package com.gun2.restest.repository;

import com.gun2.restest.config.QueryDslConfig;
import com.gun2.restest.constant.Method;
import com.gun2.restest.entity.Job;
import com.gun2.restest.entity.JobBody;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@Import(QueryDslConfig.class)
public class JobRepositoryTest {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    JobBodyRepository jobBodyRepository;

    @Nested
    @DisplayName("CRUD 테스트")
    class Crud {

        @Test
        @DisplayName("JobBody 생성 및 삭제 테스트")

        void test02() {
            /** given */
            ArrayList<JobBody> bodyList = new ArrayList<>();
            bodyList.add(JobBody.builder().build());
            bodyList.add(JobBody.builder().build());
            Job save = jobRepository.save(
                    Job.builder()
                            .jobBodyList(bodyList)
                            .build()
            );

            Job job = jobRepository.findById(save.getId()).get();

            /** when */
            int afterSaveCnt = job.getJobBodyList().size();
            job.getJobBodyList().remove(save.getJobBodyList().get(0));
            Job updatedJob = jobRepository.save(job);
            int afterUpdateCnt = updatedJob.getJobBodyList().size();

            /** then */
            assertThat(afterSaveCnt).as("job body가 2개 생성됨")
                    .isEqualTo(2);
            assertThat(afterUpdateCnt).as("job body가 1개 남음")
                    .isEqualTo(1);
        }

        @Test
        @DisplayName("삭제 테스트")
        void test01() {
            /** given */
            Job job = Job.builder().method(Method.GET).build();
            Job savedJob = jobRepository.save(job);

            /** when */
            jobRepository.delete(savedJob);

            /** then */
            Optional<Job> byId = jobRepository.findById(savedJob.getId());
            assertThat(byId.isEmpty()).isTrue();
        }

    }
}
