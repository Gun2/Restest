package com.github.gun2.managerapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/dashboard", "/job", "/scheduler", "/performance"})
    String home(){
        return "index";
    }
}
