/**
 * 현재 접근한 사용자 정보를 가지고 있는 컴포넌트
 */
package com.github.gun2.managerapp.domain.systemInformation.component;

import com.github.gun2.managerapp.domain.systemInformation.event.AccessUserChangeEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class AccessUserComponent extends Counting{
    private final ApplicationEventPublisher applicationEventPublisher;

    public AccessUserComponent(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void increaseNumber() {
        super.increaseNumber();
        applicationEventPublisher.publishEvent(new AccessUserChangeEvent(getNumber()));
    }

    @Override
    public void decreaseNumber() {
        super.decreaseNumber();
        applicationEventPublisher.publishEvent(new AccessUserChangeEvent(getNumber()));
    }
}
