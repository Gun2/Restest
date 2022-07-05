/**
 * id로 테이블 Row 질의 후 결과값을 찾지 못했을 경우 사용.
 * ex => findById가 null인경우
 */
package com.gun2.restest.exception;

public class RowNotFoundByIdException extends RuntimeException{
    private long id;
    public RowNotFoundByIdException(String message, long id){
        super(message);
        this.id = id;
    }

    public long getId() {
        return this.id;
    }
}
