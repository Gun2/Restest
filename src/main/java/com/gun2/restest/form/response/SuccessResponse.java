package com.gun2.restest.form.response;

import com.gun2.restest.constant.ResponseCode;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class SuccessResponse<T> extends BaseResponse{
    private T data;

    public SuccessResponse(T data){
        this.data = data;
    }

    public final static <T> SuccessResponse of(T data){
        return new SuccessResponse(data);
    }
}
