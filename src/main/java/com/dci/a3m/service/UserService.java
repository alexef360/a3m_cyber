package com.dci.a3m.service;

import com.dci.a3m.entity.User;
import com.dci.a3m.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserServiceInterface{

    private UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userDao) {
        this.userRepo = userDao;
    }

    @Override
    @Transactional
    public User createUser(User user) {
        return userRepo.save(user);
    }

    @Override
    public User findUserById(Long id) {
        Optional<User> result = userRepo.findById(id);
        User user = null;
        if(result.isPresent()){
            user = result.get();
        }else{
            throw new RuntimeException("User with id " + id + " not found.");
        }
        return user;
    }

    @Override
    public User updateUser(User user) {
        return userRepo.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }
}
