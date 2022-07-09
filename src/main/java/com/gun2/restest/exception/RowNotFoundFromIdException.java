/**
 * id로 테이블 Row 질의 후 결과값을 찾지 못했을 경우 사용.
 * ex => findById가 null인경우
 */
package com.gun2.restest.exception;

/**
 * <b>DB table에 id로 데이터를 질의 하였으나 값을 찾을 수 없는경우 발생</b>
 * @see org.springframework.data.jpa.repository.JpaRepository#findById(Object)
 */
public class RowNotFoundFromIdException extends RuntimeException{
    private String id;

    /**
     * @param message 에러 메시지
     * @param id 조회하려던 id
     */
    public RowNotFoundFromIdException(String message, long id){
        super(message);
        this.id = String.valueOf(id);
    }

    /**
     * @param message 에러 메시지
     * @param id 조회하려던 id
     */
    public RowNotFoundFromIdException(String message, String id){
        super(message);
        this.id = id;
    }

    /**
     * <b>조회에 사용하였던 id값 가져오기</b>
     * @return 조회 시 사용한 id값
     */
    public String getId() {
        return this.id;
    }
}
