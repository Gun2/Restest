package com.github.gun2.managerapp.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class LimitedQueueTest {

    @Test
    @DisplayName("지정한 사이즈를 초과해서 값이 삽입되는 경우 가장 오래된 값이 삭제되어 지정한 사이즈를 유지항여야함")
    void test01() {
        /** given */
        int size = 10;
        LimitedQueue<Integer> integerLimitedQueue = new LimitedQueue<>(size);
        /** when */
        for (int i = 0; i < size+10; i++) {
            integerLimitedQueue.push(i);
        }
        /** then */
        assertThat(integerLimitedQueue.toList().size()).as("지정한 사이즈를 초과하면 안됨")
                .isLessThanOrEqualTo(size);

    }
}