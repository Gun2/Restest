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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        return ErrorResponse.of(e.getFieldErrors()).toResponseEntity(ErrorCode.INVALID_INPUT_VALUE);
    }

    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        log.error("handleBindException", e);
        return ErrorResponse.of(e.getFieldErrors()).toResponseEntity(ErrorCode.INVALID_INPUT_VALUE);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException", e);
        return new ErrorResponse().toResponseEntity(ErrorCode.METHOD_NOT_ALLOWED);
    }

    /**
     * 접근 권한이 없는 경우 발생
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        log.error("handleAccessDeniedException", e);
        return new ErrorResponse().toResponseEntity(ErrorCode.HANDLE_ACCESS_DENIED);
    }

    /**
     * 없는 row를 질의하여 사용하려고 한 경우.
     * @param e
     * @return
     */
    @ExceptionHandler(RowNotFoundByIdException.class)
    protected ResponseEntity<ErrorResponse> handleRowNotFoundByIdException(RowNotFoundByIdException e){
        log.error("handleRowNotFoundByIdException", e);
        log.error("handleRowNotFoundByIdException id : {}", e.getId());
        return new ErrorResponse().toResponseEntity(ErrorCode.NOT_FOUND_BY_ID);
    }

    /**
     * id가 null인 값으로 겁색을 요청한 경우.
     * @param e
     * @return
     */
    @ExceptionHandler(IdentityIsNullException.class)
    protected ResponseEntity<ErrorResponse> handleIdentityIsNullException(IdentityIsNullException e){
        log.error("handleIdentityIsNullException", e);
        return new ErrorResponse().toResponseEntity(ErrorCode.ID_IS_NULL);
    }

    /**
     * 잘못된 타입으로 요청한 경우
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){
        log.error("handleMethodArgumentTypeMismatchException", e);
        return new ErrorResponse().toResponseEntity(ErrorCode.INVALID_TYPE_VALUE);
    }

    /**
     * 지원하지 않는 Content type 으로 요청한 경우 발생
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e){
        log.error("handleHttpMediaTypeNotSupportedException", e);
        return new ErrorResponse().toResponseEntity(ErrorCode.CONTENT_TYPE_NOT_SUPPORTED);
    }

    /**
     * 요청한 값을 읽을 수 없는 경우 발생 ex => JSON format이 옳바르지 않은경우
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e){
        log.error("handleHttpMessageNotReadableException", e);
        return new ErrorResponse().toResponseEntity(ErrorCode.INVALID_INPUT_VALUE);
    }

    /**
     * 요청한 값을 읽을 수 없는 경우 발생 ex => JSON format이 옳바르지 않은경우
     * @param e
     * @return
     */
    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    protected ResponseEntity<ErrorResponse> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException e){
        log.error("handleInvalidDataAccessApiUsageException", e);
        return new ErrorResponse().toResponseEntity(ErrorCode.INVALID_INPUT_VALUE);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e){
        return new ErrorResponse().toResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
