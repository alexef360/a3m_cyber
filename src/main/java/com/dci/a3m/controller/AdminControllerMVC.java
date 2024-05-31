package com.dci.a3m.controller;

import com.dci.a3m.entity.Admin;
import com.dci.a3m.entity.Authority;
import com.dci.a3m.entity.User;
import com.dci.a3m.service.AdminService;
import com.dci.a3m.service.UserService;
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
    UserService userService;

    @Autowired
    public AdminControllerMVC(PasswordEncoder passwordEncoder, AdminService adminService, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.adminService = adminService;
        this.userService = userService;
    }

    // CRUD OPERATIONS

    // READ ALL
    @GetMapping("/admins")
    public String findAll(Model model){
        List<Admin> admins = adminService.findAll();
        model.addAttribute("admins", admins);
        return "restricted/admins";
    }

    // READ BY ID
    @GetMapping("/admins/")
    public String getAdminById(@RequestParam("adminId") Long id, Model model){
        Admin admin = adminService.findById(id);
        if(admin == null){
            model.addAttribute("error", "Admin not found.");
            return "restricted/admin-error";
        }
        model.addAttribute("admin", admin);
        return "restricted/admin-info";
    }

    // CREATE - SHOW FORM
    @GetMapping("/admin-form")
    public String showAdminForm(Model model){
        model.addAttribute("admin", new Admin());
        return "restricted/admin-form";
    }

    // UPDATE - SHOW FORM
    @GetMapping("/admin-form-update")
    public String showUpdateAdminForm(@RequestParam("adminId") Long adminId, Model model){
        Admin admin = adminService.findById(adminId);

        model.addAttribute("admin", admin);
        return "restricted/admin-form";
    }

    //  SAVE FORM
    @PostMapping("/admin-form/create")
    public String saveAdmin(@ModelAttribute("admin") Admin admin){

        // PasswordEncoder
        User tempUser = admin.getUser();
        tempUser.setAdmin(admin);
        admin.getUser().setPassword(passwordEncoder.encode(tempUser.getPassword()));
        tempUser.setEnabled(true);
        tempUser.setAuthority(new Authority(tempUser.getUsername(), admin.getRole()));
        admin.setUser(tempUser);

        // Save Admin
        adminService.save(admin);
        userService.update(tempUser);
        return "redirect:/mvc/admins";
    }

    // UPDATE FORM
    @PostMapping("/admin-form/update")
    public String updateAdmin(@ModelAttribute("admin") Admin admin) {
        Admin existingAdmin = adminService.findById(admin.getId());

        if (existingAdmin == null) {
            return "restricted/user-error";
        }

        // Update the user details
        User tempUser = existingAdmin.getUser();
        tempUser.setEmail(admin.getUser().getEmail());
        tempUser.setUsername(admin.getUser().getUsername());

        tempUser.setAuthority(new Authority(tempUser.getUsername(), admin.getRole()));
        existingAdmin.setUser(tempUser);
        existingAdmin.setRole(admin.getRole());

        adminService.update(existingAdmin);
        userService.update(tempUser);

        return "redirect:/mvc/admins";
    }


    // DELETE BY ID
    @GetMapping("/admin-delete")
    public String deleteAdmin(@RequestParam("adminId") Long id){
        adminService.deleteById(id);
        return "redirect:/mvc/admins";
    }

}