package com.dci.a3m.controller;

import com.dci.a3m.entity.User;
import com.dci.a3m.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get")
    public String getUsers(Model model){
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "get-users";
    }

    @GetMapping("/registerUser")
    public String registerUser(Model model){
        model.addAttribute("user", new User());
        return "user-form";
    }

    @PostMapping("/registerUser")
    public String saveUser(@ModelAttribute("user") User userForm, Model model){
        userForm.setRole("ROLE_USER");
       User createdUser = userService.createUser(userForm);
       if(createdUser == null || createdUser.getId() == null){
           model.addAttribute("error", "User not created.");
       }
        return "redirect:/users/profile/create?userId="+ createdUser.getId();
    }


    @GetMapping("/profile/create")
    public String createProfile(@RequestParam("userId") Long id, Model model){
        User user = userService.findUserById(id);
        if(user == null){
            model.addAttribute("error", "User not found.");
            return "error";
        }
        model.addAttribute("user", user);
        return "profile-create";
    }

    @PostMapping("/profile/create")
    public String createProfile(@ModelAttribute("user") User userForm, Model model){
        User userDTO = userService.findUserById(userForm.getId());
        if(userDTO != null) {
            userDTO.setFirstName(userForm.getFirstName());
            userDTO.setLastName(userForm.getLastName());
            userDTO.setPhone(userForm.getPhone());
            userDTO.setBirthDate(userForm.getBirthDate());
            userDTO.setCity(userForm.getCity());
            userDTO.setCountry(userForm.getCountry());
            userDTO.setPostalCode(userForm.getPostalCode());
            userService.updateUser(userDTO);
            return "success"; // Redirect to success page after profile creation
       }
        model.addAttribute("error", "User not found.");
        return "error";
    }

}
