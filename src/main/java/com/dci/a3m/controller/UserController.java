package com.dci.a3m.controller;

import com.dci.a3m.entity.User;
import com.dci.a3m.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String createUser(Model model){
        model.addAttribute("user", new User());
        return "user-form";
    }

    @PostMapping("/user")
    public String saveUser(@ModelAttribute("user") User user){
        userService.createUser(user);
        return "redirect:/profile/create";
    }


    @GetMapping("/profile/create")
    public String createProfileForm() {

        return "profile-create";
    }

    @PostMapping("/profile")
    public String createProfile(@ModelAttribute("user") User user){


        return "success"; // Redirect to success page after profile creation
    }

}
