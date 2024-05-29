package com.dci.a3m.controller;

import com.dci.a3m.entity.Admin;
import com.dci.a3m.entity.Authority;
import com.dci.a3m.entity.User;
import com.dci.a3m.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    // UPDATE - SHOW FORM
    @GetMapping("/admin-form-update")
    public String showUpdateAdminForm(@RequestParam("adminId") Long adminId, Model model){
        Admin admin = adminService.findById(adminId);

        model.addAttribute("admin", admin);
        return "admin-form";
    }

    //  SAVE FORM - For normal form and also for Update form
    @PostMapping("/admin-form/create")
    public String createAdmin(@ModelAttribute("admin") Admin admin){

        // PasswordEncoder
        User tempUser = admin.getUser();
        tempUser.setAdmin(admin);
        admin.getUser().setPassword(passwordEncoder.encode(tempUser.getPassword()));
        tempUser.setEnabled(true);
        tempUser.setAuthority(new Authority(tempUser.getUsername(), admin.getRole()));
        admin.setUser(tempUser);

        // Save Admin
        adminService.save(admin);
        return "redirect:/mvc/admins";
    }

    @PostMapping("/mvc/admin-form/update")
    public String updateAdmin(Admin admin) {
        adminService.update(admin);
        return "redirect:/mvc/admins";
    }




    // DELETE BY ID

}
