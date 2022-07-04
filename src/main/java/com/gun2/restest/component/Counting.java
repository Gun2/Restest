/**
 * 카운트 인터페이스
 */
package com.gun2.restest.component;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class Counting {

    private AtomicInteger number = new AtomicInteger();



    public int getNumber(){
        return this.number.get();
    }

    public void setNumber(int num){
        this.number.set(num);
    }

    public void increaseNumber(){
        this.number.set(number.get() + 1);
    }
}
