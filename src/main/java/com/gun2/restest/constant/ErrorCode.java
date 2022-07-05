package com.gun2.restest.constant;

public enum ErrorCode implements ResponseCode{

    INVALID_INPUT_VALUE(400, "INVALID_INPUT_VALUE", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405,"METHOD_NOT_ALLOWED", " Invalid Input Value"),
    ENTITY_NOT_FOUND(400,"ENTITY_NOT_FOUND", " Entity Not Found"),
    NOT_FOUNT_BY_ID(400,"NOT_FOUNT_BY_ID", " Not Found By Id"),
    INTERNAL_SERVER_ERROR(500,"INTERNAL_SERVER_ERROR", "Server Error"),
    INVALID_TYPE_VALUE(400,"INVALID_TYPE_VALUE", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403 ,"HANDLE_ACCESS_DENIED", "Access is Denied");

    private int status;
    private String code;
    private String message;

    ErrorCode(int status, String code, String message){
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
