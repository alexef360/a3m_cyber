package com.dci.a3m.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {


    @GetMapping("/login-form")
    public String login() {
        return "login-form";
    }


    @GetMapping("/login-success")
    public String loginSuccess() {
        return "login-success";
    }

}


