package com.gun2.restest.controller.rest;

import com.gun2.restest.constant.SuccessCode;
import com.gun2.restest.form.response.SuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestRestController {

    @GetMapping("/v1/test")
    public ResponseEntity hello(){
        return new SuccessResponse("hello").toResponseEntity(SuccessCode.OK);
    }
}
