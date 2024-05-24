package com.dci.a3m.service;

import com.dci.a3m.entity.User;
import com.dci.a3m.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceInterface{

    private UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userDao) {
        this.userRepo = userDao;
    }

    // CRUD OPERATIONS

    // READ ALL
    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    // READ BY ID
    @Override
    public User findById(Long id) {
        Optional<User> result = userRepo.findById(id);
        User user = null;
        if(result.isPresent()){
            user = result.get();
        }else{
            throw new RuntimeException("User with id " + id + " not found.");
        }
        return user;
    }


    // CREATE
    @Override
    @Transactional
    public User save(User user) {
        return userRepo.save(user);
    }

    // UPDATE
    @Override
    @Transactional
    public User update(User user) {
        return userRepo.save(user);
    }

    // DELETE
    @Override
    @Transactional
    public void deleteById(Long id) {
        userRepo.deleteById(id);
    }
}
