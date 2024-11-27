package com.github.gun2.managerapp.util;

import io.swagger.models.auth.In;

import java.util.Random;

public class ColorUtil {
    private static final int COLOR_MOD = 100;
    private final Random RANDOM = new Random();

    public String generateHexColor(){
        int randNum = RANDOM.nextInt(0xffffff + 1);
        String hexColor = String.format("#%06x", randNum);
        return hexColor;
    }


    /**
     * <p>특정 값 이상의 색상 랜덤으로 생성</p>
     * @return
     */
    public String generateLightHexColor(){
        String hexColor = generateHexColor();

        String red = hexModUp(hexColor.substring(1,3), COLOR_MOD);
        String green = hexModUp(hexColor.substring(3,5), COLOR_MOD);
        String blue = hexModUp(hexColor.substring(5,7), COLOR_MOD);
        return "#"+red+green+blue;
    }

    /**
     * <p>hex값 모듈러스 계산</p>
     * @param hex
     * @param mod
     * @return
     */
    public String hexModUp(String hex, int mod){
        int dec = Integer.parseInt(hex, 16);
        return Integer.toHexString(255 - dec % mod);
    }
}
