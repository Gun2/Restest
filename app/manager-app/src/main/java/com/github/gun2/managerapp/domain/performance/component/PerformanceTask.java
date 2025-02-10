package com.github.gun2.managerapp.domain.performance.component;

import com.github.gun2.managerapp.config.RestTemplateConfig;
import com.github.gun2.managerapp.domain.scheduler.dto.HttpResponseDto;
import com.github.gun2.managerapp.domain.job.dto.JobBodyDto;
import com.github.gun2.managerapp.domain.job.dto.JobDto;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * <p>성능측정 타겟 정보</p>
 */
@Setter
@Getter
@Slf4j
public class PerformanceTask{
    private JobDto jobDto;
    //성공횟수
    private AtomicInteger successCount = new AtomicInteger();
    //실패횟수
    private AtomicInteger failureCount = new AtomicInteger();
    private long minMillisecond = Long.MAX_VALUE;
    private long maxMillisecond = 0;

    private List<Thread> runThreadList = new ArrayList<>();
    private long startTimeMillisecond;
    private long stopTimeMillisecond;

    public PerformanceTask(JobDto jobDto, int instance){
        this.jobDto = jobDto;
        for (int i = 0; i < instance; i++) {
            Thread jobThread = createJobThread(jobDto);
            runThreadList.add(jobThread);
        }
    }

    /**
     * <p>task를 시작함</p>
     */
    public void start(){
        runThreadList.forEach(thread -> {
            if(!thread.isAlive()){
                thread.start();
            }
        });
        startTimeMillisecond = System.currentTimeMillis();
    }

    /**
     * <p>task를 중단함</p>
     */
    public void stop(){
        stopTimeMillisecond = System.currentTimeMillis();
        runThreadList.forEach(thread -> thread.interrupt());
    }

    public void join(){
        runThreadList.forEach(thread -> {
            try {
                thread.join(60000);
            }catch (Exception e){
                log.error("join error : ", e);
            }

        });
    }

    /**
     * <p>수행시간의 최소값 최대값을 확인하여 적용함.</p>
     * @param time
     */
    private void checkMinMax(long time){
        if(minMillisecond > time){
            minMillisecond = time;
        }
        if(maxMillisecond < time){
            maxMillisecond = time;
        }
    }



    private Thread createJobThread(JobDto jobDto){
        return new Thread(() -> {
            RestTemplate restTemplate = RestTemplateConfig.createElseGetNull();
            while (!Thread.currentThread().isInterrupted()) {
                List<JobBodyDto> usableBody = jobDto.getJobBodyList()
                        .stream()
                        .filter(body -> body.isUsable())
                        .toList();
                HttpHeaders headers = new HttpHeaders();
                jobDto.getJobHeaderList().stream()
                        .filter(header ->
                                StringUtils.isNoneBlank(header.getKeyName()) && header.isUsable())
                        .forEach(header -> {
                            headers.add(header.getKeyName(), header.getValue());
                        });

                usableBody.forEach(body -> {
                    requestBody(jobDto, body, headers, restTemplate);
                });
            }
        });

    }

    private void requestBody(JobDto job,
                             JobBodyDto body,
                             HttpHeaders headers,
                             RestTemplate restTemplate) {
        long startTime = System.currentTimeMillis();
        HttpResponseDto httpResponseDto = new HttpResponseDto();
        try {
            HttpEntity<String> entity = new HttpEntity<String>(body.getBody(), headers);

            String url = job.getUrl();
            HttpMethod httpMethod = HttpMethod.valueOf(job.getMethod().name());

            httpResponseDto.setRequest(entity,url, job.getMethod().name());

            ResponseEntity<String> response = restTemplate.exchange(url, httpMethod, entity, String.class);
            httpResponseDto.setResponse(response);
            successCount.incrementAndGet();
        } catch (Exception e) {
            httpResponseDto.setErrorResponse(e);
            log.error("Exception : {}", e);
            failureCount.incrementAndGet();
        }
        try {
            long time = System.currentTimeMillis() - startTime;
            checkMinMax(time);
            httpResponseDto.setTime(time);
        } catch (Exception e) {
            e.printStackTrace();
            Thread.currentThread().interrupted();
        }
    }

    /**
     * <p>성공, 실패 총 횟수 합산하여 반환</p>
     * @return
     */
    public int getTotalRequestCount(){
        return successCount.get() + failureCount.get();
    }

    public long getMinMillisecond() {
        return minMillisecond == Long.MAX_VALUE ? 0 : minMillisecond;
    }
}
