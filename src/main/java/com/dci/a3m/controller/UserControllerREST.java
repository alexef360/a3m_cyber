package com.dci.a3m.controller;


import com.dci.a3m.entity.User;
import com.dci.a3m.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserControllerREST {

    private final UserService userService;

    public UserControllerREST(UserService userService) {
        this.userService = userService;
    }

    // CRUD OPERATIONS

    // READ ALL
    @GetMapping("/users")
    public List<User> findAll() {
        return userService.findAll();
    }


    // READ BY ID
    @GetMapping("/users/{userId}")
    public User findUserById(@PathVariable Long userId) {
        User user = userService.findById(userId);

        // check if there is a user
        if (user == null) {
            throw new RuntimeException("User with id " + userId + " not found.");
        }
        return user;
    }


    // CREATE
    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }

    // UPDATE
    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        return userService.update(user);
    }

    // DELETE
    @DeleteMapping("/users/{userId}")
    public String deleteById(@PathVariable Long userId) {
        // check if there is a user
        if (userService.findById(userId) == null) {
            throw new RuntimeException("User with id " + userId + " not found.");
        }
        userService.deleteById(userId);
        return "User with id " + userId + " deleted.";


    }
}
