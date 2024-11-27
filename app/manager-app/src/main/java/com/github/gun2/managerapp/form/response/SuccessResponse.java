package com.github.gun2.managerapp.form.response;

import lombok.Getter;

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
