/**
 * 서버 가동 이후 테스트 성공한 개수 정보를 가지는 컴포넌트
 */
package com.github.gun2.managerapp.domain.systemInformation.component;

import com.github.gun2.managerapp.domain.systemInformation.event.SuccessCountChangeEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class SuccessCountComponent extends Counting{
    private final ApplicationEventPublisher applicationEventPublisher;

    public SuccessCountComponent(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void increaseNumber() {
        super.increaseNumber();
        applicationEventPublisher.publishEvent(new SuccessCountChangeEvent(getNumber()));
    }

    @Override
    public void decreaseNumber() {
        super.decreaseNumber();
        applicationEventPublisher.publishEvent(new SuccessCountChangeEvent(getNumber()));
    }
}
