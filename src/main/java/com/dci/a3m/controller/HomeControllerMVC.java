package com.dci.a3m.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeControllerMVC {

    @GetMapping("/")
    public String home() {
        return "home-links";
    }

    @GetMapping("/home")
    public String homehome() {
        return "home-links";
    }

}
