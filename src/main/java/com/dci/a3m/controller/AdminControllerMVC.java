package com.dci.a3m.controller;

import com.dci.a3m.entity.Admin;
import com.dci.a3m.entity.Authority;
import com.dci.a3m.entity.User;
import com.dci.a3m.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/mvc")
public class AdminControllerMVC {

    AdminService adminService;
    PasswordEncoder passwordEncoder;

    @Autowired
    public AdminControllerMVC(PasswordEncoder passwordEncoder, AdminService adminService) {
        this.passwordEncoder = passwordEncoder;
        this.adminService = adminService;
    }

    // CRUD OPERATIONS

    // READ ALL
    @GetMapping("/admins")
    public String findAll(Model model){
        List<Admin> admins = adminService.findAll();
        model.addAttribute("admins", admins);
        return "admins";
    }

    // READ BY ID
    @GetMapping("/admins/{id}")
    public String getAdminById(Model model, Long id){
        Admin admin = adminService.findById(id);
        if(admin == null){
            model.addAttribute("error", "Admin not found.");
            return "admin-error";
        }
        model.addAttribute("admin", admin);
        return "admin-info";
    }

    // CREATE - SHOW FORM
    @GetMapping("/admin-form")
    public String showAdminForm(Model model){
        model.addAttribute("admin", new Admin());
        return "admin-form";
    }

    // CREATE - SAVE FORM
    @PostMapping("/admin-form")
    public String addAdmin(@ModelAttribute Admin admin){
//        String username = admin.getUser().getUsername();
//        String password = passwordEncoder.encode(username);
//        String email = admin.getUser().getEmail();
//        adminService.createAdmin(username, email, password);


        /// TODO MAKE IT EASIER
        // PasswordEncoder
        User tempUser = admin.getUser();
        tempUser.setAdmin(admin);
        tempUser.setPassword(passwordEncoder.encode(tempUser.getPassword()));
        tempUser.setEnabled(true);
        tempUser.setAuthority(new Authority(tempUser.getUsername(), admin.getRole()));

        admin.setUser(tempUser);

        // Save Admin
        adminService.save(admin);
        return "redirect:/admin-list";
    }





    // UPDATE

    // DELETE BY ID

}
