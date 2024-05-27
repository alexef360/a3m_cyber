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


    @GetMapping("/a")
    public String home(Model model) {
        model.addAttribute("pageTitle", "A3M");
        model.addAttribute("userName", "Mahmoud Najmeh");
        return "a3m";
    }

    // CRUD OPERATIONS

    // READ ALL
    @GetMapping("/users")
    public String findAll(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        model.addAttribute("pageTitle", "A3M");
        return "user-list";
    }

    // READ BY ID
    @GetMapping("/user/{id}")
    public String getUserById(Model model, @PathVariable("id") Long id){
        User user = userService.findById(id);
        if(user == null){
            model.addAttribute("error", "User not found.");
            return "user-error";
        }
        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "A3M");
        return "user-info";
    }

    // CREATE - SHOWFORM - CREADENTIALS
    @GetMapping("/user-credential-form")
    public String registerUser(Model model){
        model.addAttribute("user", new User());
        model.addAttribute("pageTitle", "A3M");
        return "user-credential-form";
    }


    @PostMapping("/user-credential-form")
    public String saveUser(@ModelAttribute("user") User userForm, Model model){
        model.addAttribute("pageTitle", "A3M");
        userForm.setRole("ROLE_USER");
        userForm.setCreatedAt(LocalDate.now());
       User createdUser = userService.save(userForm);
       if(createdUser == null || createdUser.getId() == null){
           model.addAttribute("error", "User not created.");
       }
        return "redirect:/mvc/user-profile-form?userId="+ createdUser.getId();
    }

    // CREATE - SHOWFORM - PROFILE
    @GetMapping("/user-profile-form")
    public String createProfileForm(@RequestParam("userId") Long id, Model model){
        User user = userService.findById(id);
        if(user == null){
            model.addAttribute("error", "User not found.");
            return "user-error";
        }
        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "A3M");
        return "user-profile-form";
    }

    @PostMapping("/user-profile-form")
    public String createProfile(@ModelAttribute("user") User userForm, Model model){
        model.addAttribute("pageTitle", "A3M");
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
            return "user-success"; // Redirect to success page after profile creation
       }
        model.addAttribute("error", "User not found.");
        return "user-error";
    }

    // DELETE
    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam("userId") Long id, Model model){
        model.addAttribute("pageTitle", "A3M");
        userService.deleteById(id);
        return "redirect:/mvc/users";
    }

}
