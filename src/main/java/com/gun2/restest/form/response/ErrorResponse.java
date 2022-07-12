package com.gun2.restest.form.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse extends BaseResponse{

    private List<ClientFieldError> errors = new ArrayList<>();


    public ErrorResponse(List<FieldError> errors){
        this.errors = errors.stream().map(ClientFieldError::new).toList();
    }

    public final static ErrorResponse of(List<FieldError> errors){
        return new ErrorResponse(errors);
    }



    @Getter
    class ClientFieldError{
        String field;
        String defaultMessage;
        ClientFieldError(FieldError fieldError){
            this.field = fieldError.getField();
            this.defaultMessage = fieldError.getDefaultMessage();
        }
    }
}