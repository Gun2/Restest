package com.gun2.restest.exception;

import com.gun2.restest.constant.ErrorCode;
import com.gun2.restest.form.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.nio.file.AccessDeniedException;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * <b>{@link org.springframework.web.bind.annotation.RequestBody}로 받은 값 중 Validation 실패 시 발생</b>
     * @param e {@link MethodArgumentNotValidException}
     * @return {@link ResponseEntity}
     * @see org.springframework.validation.annotation.Validated
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        return ErrorResponse.of(e.getFieldErrors()).toResponseEntity(ErrorCode.INVALID_INPUT_VALUE);
    }

    /**
     * <b>{@link org.springframework.web.bind.annotation.ModelAttribute}로 받은 값 중 validation 실패 시 발생</b>
     * @param e {@link BindException}
     * @return {@link ResponseEntity}
     * @see org.springframework.validation.annotation.Validated
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        log.error("handleBindException", e);
        return ErrorResponse.of(e.getFieldErrors()).toResponseEntity(ErrorCode.INVALID_INPUT_VALUE);
    }

    /**
     * <b>지원하지 않는 메소드 호출 시 발생</b>
     * @param e {@link HttpRequestMethodNotSupportedException}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException", e);
        return new ErrorResponse().toResponseEntity(ErrorCode.METHOD_NOT_ALLOWED);
    }

    /**
     * <b>접근 권한이 없는 경우 발생</b>
     * @param e {@link AccessDeniedException}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        log.error("handleAccessDeniedException", e);
        return new ErrorResponse().toResponseEntity(ErrorCode.HANDLE_ACCESS_DENIED);
    }

    /**
     * <b>DB table에 id로 데이터를 질의 하였으나 값을 찾을 수 없는경우 발생</b>
     * @param e {@link RowNotFoundFromIdException}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(RowNotFoundFromIdException.class)
    protected ResponseEntity<ErrorResponse> handleRowNotFoundByIdException(RowNotFoundFromIdException e){
        log.error("handleRowNotFoundByIdException", e);
        log.error("handleRowNotFoundByIdException id : {}", e.getId());
        return new ErrorResponse().toResponseEntity(ErrorCode.NOT_FOUND_BY_ID);
    }

    /**
     * <b>id가 null인 값으로 겁색을 요청한 경우.</b>
     * @param e {@link IdentityIsNullException}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(IdentityIsNullException.class)
    protected ResponseEntity<ErrorResponse> handleIdentityIsNullException(IdentityIsNullException e){
        log.error("handleIdentityIsNullException", e);
        return new ErrorResponse().toResponseEntity(ErrorCode.ID_IS_NULL);
    }

    /**
     * <b>잘못된 타입으로 요청한 경우</b>
     * @param e {@link MethodArgumentTypeMismatchException}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){
        log.error("handleMethodArgumentTypeMismatchException", e);
        return new ErrorResponse().toResponseEntity(ErrorCode.INVALID_TYPE_VALUE);
    }

    /**
     * <b>지원하지 않는 Content type 으로 요청한 경우 발생</b>
     * @param e {@link HttpMediaTypeNotSupportedException}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e){
        log.error("handleHttpMediaTypeNotSupportedException", e);
        return new ErrorResponse().toResponseEntity(ErrorCode.CONTENT_TYPE_NOT_SUPPORTED);
    }

    /**
     * <b>요청한 값을 읽을 수 없는 경우 발생 ex => JSON format이 옳바르지 않은경우</b>
     * @param e {@link HttpMessageNotReadableException}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        log.error("handleHttpMessageNotReadableException", e);
        return new ErrorResponse().toResponseEntity(ErrorCode.INVALID_INPUT_VALUE);
    }

    /**
     * <b>요청한 값을 읽을 수 없는 경우 발생 ex => JSON format이 옳바르지 않은경우</b>
     * @param e {@link InvalidDataAccessApiUsageException}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    protected ResponseEntity<ErrorResponse> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException e){
        log.error("handleInvalidDataAccessApiUsageException", e);
        return new ErrorResponse().toResponseEntity(ErrorCode.INVALID_INPUT_VALUE);
    }

    /**
     * <b>기타 정의하지 않은 예외 발생 시</b>
     * @param e {@link Exception}
     * @return {@link ResponseEntity}
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e){
        return new ErrorResponse().toResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
