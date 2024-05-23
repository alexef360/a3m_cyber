package com.dci.a3m.service;

import com.dci.a3m.entity.User;

import java.util.List;

public interface UserServiceInterface {

    // User CRUD
    public User createUser(User user);

    public User findUserById(Long id);

    public User updateUser(User user);

    public void deleteUser(Long id);
}
