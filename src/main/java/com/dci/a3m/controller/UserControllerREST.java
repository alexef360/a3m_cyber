package com.dci.a3m.controller;


import com.dci.a3m.entity.User;
import com.dci.a3m.service.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserControllerREST {

    private final UserServiceImpl userServiceImpl;

    public UserControllerREST(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    // CRUD OPERATIONS

    // READ ALL
    @GetMapping("/users")
    public List<User> findAll() {
        return userServiceImpl.findAll();
    }


    // READ BY ID
    @GetMapping("/users/{userId}")
    public User findUserById(@PathVariable Long userId) {
        User user = userServiceImpl.findById(userId);

        // check if there is a user
        if (user == null) {
            throw new RuntimeException("User with id " + userId + " not found.");
        }
        return user;
    }


    // CREATE
    @PostMapping("/users")
    public void createUser(@RequestBody User user) {
       userServiceImpl.save(user);
    }

    // UPDATE
    @PutMapping("/users")
    public void updateUser(@RequestBody User user) {
         userServiceImpl.update(user);
    }

    // DELETE
    @DeleteMapping("/users/{userId}")
    public String deleteById(@PathVariable Long userId) {
        // check if there is a user
        if (userServiceImpl.findById(userId) == null) {
            throw new RuntimeException("User with id " + userId + " not found.");
        }
        userServiceImpl.deleteById(userId);
        return "User with id " + userId + " deleted.";


    }
}
