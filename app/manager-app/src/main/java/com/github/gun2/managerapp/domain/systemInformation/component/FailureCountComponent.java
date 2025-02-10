/**
 * 서버 가동 이후 테스트 성공한 개수 정보를 가지는 컴포넌트
 */
package com.github.gun2.managerapp.domain.systemInformation.component;

import com.github.gun2.managerapp.domain.systemInformation.event.FailureCountChangeEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class FailureCountComponent extends Counting{
    private final ApplicationEventPublisher applicationEventPublisher;

    public FailureCountComponent(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void increaseNumber() {
        super.increaseNumber();
        applicationEventPublisher.publishEvent(new FailureCountChangeEvent(getNumber()));
    }

    @Override
    public void decreaseNumber() {
        super.decreaseNumber();
        applicationEventPublisher.publishEvent(new FailureCountChangeEvent(getNumber()));
    }
}
