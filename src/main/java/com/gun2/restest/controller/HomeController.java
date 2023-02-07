package com.gun2.restest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/dashboard", "/job", "/scheduler", "/performance"})
    String home(){
        return "index";
    }
}
