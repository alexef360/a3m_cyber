package com.dci.a3m.service;

import com.dci.a3m.entity.User;

import java.util.List;

public interface UserServiceInterface {

    // User CRUD
    List<User> findAllUsers();
    User createUser(User user);

    User findUserById(Long id);

    void deleteUser(Long id);
}
