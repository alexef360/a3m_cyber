package com.dci.a3m.controller;

import com.dci.a3m.entity.User;
import com.dci.a3m.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/mvc")
public class UserControllerMVC {

    private UserService userService;

    @Autowired
    public UserControllerMVC(UserService userService) {
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


    // CREATE
    @GetMapping("/registerUser")
    public String registerUser(Model model){
        model.addAttribute("user", new User());
        return "user-form";
    }


    @PostMapping("/registerUser")
    public String saveUser(@ModelAttribute("user") User userForm, Model model){
        userForm.setRole("ROLE_USER");
        userForm.setCreatedAt(LocalDate.now());
       User createdUser = userService.save(userForm);
       if(createdUser == null || createdUser.getId() == null){
           model.addAttribute("error", "User not created.");
       }
        return "redirect:/users/profile/create?userId="+ createdUser.getId();
    }


    @GetMapping("/profile/create")
    public String createProfileForm(@RequestParam("userId") Long id, Model model){
        User user = userService.findById(id);
        if(user == null){
            model.addAttribute("error", "User not found.");
            return "error";
        }
        model.addAttribute("user", user);
        return "profile-create";
    }

    @PostMapping("/profile/save")
    public String createProfile(@ModelAttribute("user") User userForm, Model model){
        User userDTO = userService.findById(userForm.getId());
        if(userDTO != null) {
            userDTO.setFirstName(userForm.getFirstName());
            userDTO.setLastName(userForm.getLastName());
            userDTO.setPhone(userForm.getPhone());
            userDTO.setBirthDate(userForm.getBirthDate());
            userDTO.setCity(userForm.getCity());
            userDTO.setCountry(userForm.getCountry());
            userDTO.setPostalCode(userForm.getPostalCode());
            userDTO.setProfilePicture(userForm.getProfilePicture());
            userService.update(userDTO);
            return "success"; // Redirect to success page after profile creation
       }
        model.addAttribute("error", "User not found.");
        return "error";
    }

    // DELETE
    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam("userId") Long id, Model model){
        userService.deleteById(id);
        return "redirect:/users/get";
    }

}
