package com.dci.a3m.service;

import com.dci.a3m.entity.User;

import java.util.List;

public interface UserServiceInterface {

    // CRUD OPERATIONS

    // READ ALL
    List<User> findAll();

    // READ BY ID
    User findById(Long id);

    // CREATE
    User save(User user);

    // UPDATE
    User update(User user);

    // DELETE BY ID
    void deleteById(Long id);


}
