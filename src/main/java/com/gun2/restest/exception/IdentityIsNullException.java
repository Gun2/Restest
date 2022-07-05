/**
 * id가 필수적인 상황에서 null값을 가지고 있을 경우 사용
 * ex => findById 의 파라미터인 ID가 null인경우
 */
package com.gun2.restest.exception;

public class IdentityIsNullException extends RuntimeException{
    public IdentityIsNullException(String message){
        super(message);
    }
}
