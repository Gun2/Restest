package com.gun2.restest.constant;

public enum SuccessCode implements ResponseCode{
    OK(200, "OK", "Success"),
    CREATED(201, "CREATED", "Created");

    private int status;
    private String code;
    private String message;

    SuccessCode(int status, String code, String message){
        this.status = status;
        this.code = code;
        this.message = message;
    }


    @Override
    public int getStatus() {
        return this.status;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}