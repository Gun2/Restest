package com.github.gun2.managerapp.domain.spreader.event;

import com.github.gun2.managerapp.domain.schedule.event.ScheduleDataChangeEvent;
import com.github.gun2.managerapp.domain.spreader.ScheduleChangeDataSpreader;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 스케줄 도메인의 변경사항을 전파하는 리스너
 */
@Component
public class ScheduleDataChangeEventListener {
    private final ScheduleChangeDataSpreader scheduleChangeDataSpreader;

    public ScheduleDataChangeEventListener(ScheduleChangeDataSpreader scheduleChangeDataSpreader) {
        this.scheduleChangeDataSpreader = scheduleChangeDataSpreader;
    }

    @EventListener(ScheduleDataChangeEvent.class)
    void scheduleDataChangeEventListener(ScheduleDataChangeEvent event) {
        switch (event.getType()){
            case CREATE -> {
                scheduleChangeDataSpreader.create(event.getData());
            }
            case UPDATE -> {
                scheduleChangeDataSpreader.update(event.getData());
            }
            case DELETE -> {
                scheduleChangeDataSpreader.delete(event.getData().getId());
            }
        }
    }
}
