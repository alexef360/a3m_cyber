package com.dci.a3m.service;

import com.dci.a3m.entity.Admin;
import com.dci.a3m.entity.Authority;
import com.dci.a3m.entity.User;
import com.dci.a3m.repository.AdminRepository;
import com.dci.a3m.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Initial Records for Admin in the Database
    @PostConstruct
    public void initAdmin() {

        // password = username
        String username = "AdminExample";
        String password = passwordEncoder.encode(username);
        String email = "admin@example.com";
        createAdmin(username, email, password);
    }


    // CRUD OPERATIONS

    // READ ALL
    @Override
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    // READ BY ID
    @Override
    public Admin findById(Long id) {
        Optional<Admin> result = adminRepository.findById(id);

        Admin admin = null;

        if (result.isPresent()) {
            admin = result.get();
        } else {
            throw new RuntimeException("Admin with id " + id + " not found.");
        }

        return admin;
    }

    // SAVE
    @Override
    public void save(Admin admin) {
        adminRepository.save(admin);
    }

    // CREATE
    @Override
    public void createAdmin(
            String username,
            String email,
            String password) {

        Admin admin = new Admin();

        Authority authority = new Authority(username, admin.getRole());

        User user = new User(username, email, password, true, authority, admin);

        userRepository.save(user);
    }

    // UPDATE
    @Override
    public void update(Admin admin) {
        adminRepository.save(admin);
    }


    // DELETE BY ID
    @Override
    public void deleteById(Long id) {
        adminRepository.deleteById(id);
    }


}
