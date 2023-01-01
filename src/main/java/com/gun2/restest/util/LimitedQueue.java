package com.gun2.restest.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * <p>지정된 size를 유지하는 queue</p>
 * @param <T>
 */
public class LimitedQueue<T> {
    private final int size;
    private Queue<T> queue = new LinkedList();
    public LimitedQueue(int size){
        this.size = size;
    }

    public void push(T data){
        queue.add(data);
        if(queue.size() > size){
            queue.poll();
        }
    }

    public List<T> toList(){
        return queue.stream().toList();
    }
}
