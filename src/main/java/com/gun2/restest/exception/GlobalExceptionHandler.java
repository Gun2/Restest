package com.gun2.restest.exception;

import com.gun2.restest.constant.ErrorCode;
import com.gun2.restest.form.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;


@RestController
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

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        log.error("handleAccessDeniedException", e);
        return new ErrorResponse().toResponseEntity(ErrorCode.HANDLE_ACCESS_DENIED);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<ErrorResponse> handleRowNotFoundByIdException(RowNotFoundByIdException e){
        log.error("handleRowNotFoundByIdException", e);
        log.error("handleRowNotFoundByIdException id : {}", e.getId());
        return new ErrorResponse().toResponseEntity(ErrorCode.NOT_FOUNT_BY_ID);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e){
        return new ErrorResponse().toResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
