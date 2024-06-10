package com.dci.a3m.controller;



import com.dci.a3m.entity.Member;
import com.dci.a3m.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginControllerMVC {
    private final MemberService memberService;

    @Autowired
    public LoginControllerMVC(MemberService memberService) {
        this.memberService = memberService;
    }


    @GetMapping("/login-form")
    public String login() {
        return "login-form";
    }


    @GetMapping("/login-success")
    public String loginSuccess() {

        Member member = memberService.getAuthenticatedMember();

        if (member == null) {
            return "redirect:/mvc/members";
        }

        return "redirect:/mvc/members/?memberId="+ member.getId();

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


