package com.github.gun2.managerapp.service;

import com.github.gun2.managerapp.dto.PerformanceDto;
import com.github.gun2.managerapp.form.request.PerformanceCreateRequest;
import org.springframework.validation.BindingResult;

public interface PerformanceService {

    /**
     * <p>instance 최대 값을 가져온다.</p>
     * @return
     */
    int getMaxInstance();

    /**
     * <p>성능측정 최대 선택 가능한 task 개수를 가져온다.</p>
     * @return
     */
    int getMaxJob();

    /**
     * <p>성능측정 생성 유효성 검사</p>
     * @param performanceCreateRequest
     * @param bindingResult
     */
    void validate(PerformanceCreateRequest performanceCreateRequest, BindingResult bindingResult);

    /**
     * <p>성능측정 정보 생성</p>
     * @param performanceCreateRequest
     * @return
     */
    PerformanceDto create(PerformanceCreateRequest performanceCreateRequest);

    /**
     * <p>성능층정 동작</p>
     * @param uuid 동작시킬 성능측정 uuid
     */
    void start(String uuid);

    /**
     * <p>성능측정 실행 중지</p>
     * @param uuid 중지할 성능측정 uuid
     */
    void stop(String uuid);
}
