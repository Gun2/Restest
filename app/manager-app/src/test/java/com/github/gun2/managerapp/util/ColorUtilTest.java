package com.github.gun2.managerapp.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class ColorUtilTest {
    ColorUtil colorUtil = new ColorUtil();

    @RepeatedTest(10)
    void colorGenTest() {
        /** given */

        /** when */
        String hexColor = colorUtil.generateHexColor();

        /** then */
        assertThat(hexColor).startsWith("#");
        assertThat(hexColor.length()).isEqualTo(7);
        log.info(hexColor);
    }

    @RepeatedTest(10)
    void colorGenTest2() {
        /** given */

        /** when */
        String hexColor = colorUtil.generateLightHexColor();
        /** then */
        assertThat(hexColor).startsWith("#");
        assertThat(hexColor.length()).isEqualTo(7);
        log.info(hexColor);
    }
}