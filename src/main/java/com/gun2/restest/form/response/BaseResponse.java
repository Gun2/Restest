package com.gun2.restest.form.response;

import com.gun2.restest.constant.ResponseCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@NoArgsConstructor
public abstract class BaseResponse {
    private String code = "";
    private String message = "";

    public ResponseEntity toResponseEntity(ResponseCode responseCode){
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
        return ResponseEntity.status(responseCode.getStatus()).body(this);
    }
}
