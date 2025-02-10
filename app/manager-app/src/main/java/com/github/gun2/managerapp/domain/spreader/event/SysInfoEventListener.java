package com.github.gun2.managerapp.domain.spreader.event;

import com.github.gun2.managerapp.domain.spreader.SysInfoDataSpreader;
import com.github.gun2.managerapp.domain.systemInformation.event.AccessUserChangeEvent;
import com.github.gun2.managerapp.domain.systemInformation.event.FailureCountChangeEvent;
import com.github.gun2.managerapp.domain.systemInformation.event.SuccessCountChangeEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 시스템 정보의 변경사항을 전파하는 리스너
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class SysInfoEventListener {

    private final SysInfoDataSpreader sysInfoDataSpreader;

    @EventListener(AccessUserChangeEvent.class)
    void accessUserChangeEventListener(AccessUserChangeEvent event){
        sysInfoDataSpreader.spreadAccessUserCount();
    }

    @EventListener(SuccessCountChangeEvent.class)
    void successCountChangeEventListener(SuccessCountChangeEvent event) {
        sysInfoDataSpreader.spreadSuccessCount();
    }

    @EventListener(FailureCountChangeEvent.class)
    void failureCountChangeEvent(FailureCountChangeEvent event) {
        sysInfoDataSpreader.spreadFailureCount();
    }
}
