package com.dci.a3m.service;

import com.dci.a3m.entity.User;

import java.util.List;

public interface UserServiceInterface {

    // User CRUD
    List<User> findAll();
    User save(User user);

    User findById(Long id);

    void deleteById(Long id);

    User update(User user);
}
