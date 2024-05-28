package com.dci.a3m.controller;

import com.dci.a3m.entity.Authority;
import com.dci.a3m.entity.User;
import com.dci.a3m.service.UserService;
import com.dci.a3m.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mvc")
public class UserControllerMVC {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserControllerMVC(UserServiceImpl userService) {
        this.userService = userService;
    }

    // CRUD OPERATIONS

    // READ ALL
    @GetMapping("/users")
    public String findAll(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "user-list";
    }


    // READ BY ID
    @GetMapping("/users/{id}")
    public String getUserById(Model model, @PathVariable("id") Long id){
        User user = userService.findById(id);
        if(user == null){
            model.addAttribute("error", "User not found.");
            return "user-error";
        }
        model.addAttribute("user", user);
        return "user-info";
    }

    // SHOW - FORM
    @GetMapping("/user-form")
    public String registerUser(Model model){
        model.addAttribute("user", new User());

        return "user-form";
    }



    // SAVE
    @PostMapping("/user-form")
    public String saveUser(@ModelAttribute("user") User user){

        //PasswordEncoder
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        user.setAuthority(new Authority(user.getUsername(), "ROLE_USER"));

        // Save the user
        userService.save(user);

        return "redirect:/mvc/users";
    }





    // DELETE
    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam("userId") Long id, Model model){
        model.addAttribute("pageTitle", "A3M");
        userService.deleteById(id);
        return "redirect:/mvc/users";
    }

}
