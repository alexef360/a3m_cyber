package com.dci.a3m.controller;



import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginControllerMVC {


    @GetMapping("/login-form")
    public String login() {
        return "login-form";
    }


    @GetMapping("/login-success")
    public String loginSuccess() {
        return "login-success";
    }

    @GetMapping("/logout")
        public String logout(HttpServletRequest request, HttpServletResponse response) {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication != null) {
                new SecurityContextLogoutHandler().logout(request, response, authentication);
            }

            return "redirect:/login-form?logout";

    }

}


