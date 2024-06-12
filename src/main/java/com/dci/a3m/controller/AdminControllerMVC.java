package com.dci.a3m.controller;

import com.dci.a3m.entity.Admin;
import com.dci.a3m.entity.Member;
import com.dci.a3m.entity.User;
import com.dci.a3m.service.AdminService;
import com.dci.a3m.service.MemberService;
import com.dci.a3m.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/mvc")
public class AdminControllerMVC {

    AdminService adminService;
    PasswordEncoder passwordEncoder;
    UserService userService;
    MemberService memberService;

    @Autowired
    public AdminControllerMVC(PasswordEncoder passwordEncoder, AdminService adminService, UserService userService, MemberService memberService) {
        this.passwordEncoder = passwordEncoder;
        this.adminService = adminService;
        this.userService = userService;
        this.memberService = memberService;
    }

    // CRUD OPERATIONS

    // READ
    @GetMapping("/admin-dashboard")
    public String adminDashboard() {
        return "restricted/admin-dashboard";
    }

    // READ ALL MEMBERS
    @GetMapping("/members-list")
    public String membersList(Model model) {
        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);
        return "restricted/members-list";
    }


    // BLOCK A MEMBER
    @PostMapping("/member-block")
    public String blockMember(@RequestParam("memberId") Long id, @RequestParam("enabled") boolean enabled, RedirectAttributes redirectAttributes) {
        Member member = memberService.findById(id);
        if (member == null) {
            redirectAttributes.addFlashAttribute("error", "Member not found.");
            return "redirect:/mvc/members-list";
        }
        User user = member.getUser();
        user.setEnabled(enabled);
        userService.update(user);
        redirectAttributes.addFlashAttribute("success", "Member has been blocked.");
        return "redirect:/mvc/members-list";
    }

    // UNBLOCK A MEMBER
    @PostMapping("/member-unblock")
    public String unblockMember(@RequestParam("memberId") Long id, @RequestParam("enabled") boolean enabled, RedirectAttributes redirectAttributes) {
        Member member = memberService.findById(id);
        if (member == null) {
            redirectAttributes.addFlashAttribute("error", "Member not found.");
            return "redirect:/mvc/members-list";
        }
        User user = member.getUser();
        user.setEnabled(enabled);
        userService.update(user);
        redirectAttributes.addFlashAttribute("success", "Member has been unblocked.");
        return "redirect:/mvc/members-list";
    }

    // READ ALL ADMINS
    @GetMapping("/admins-list")
    public String adminsList(Model model) {
        List<Admin> admins = adminService.findAll();
        model.addAttribute("admins", admins);
        return "restricted/admins-list";
    }

    // BLOCK AN ADMIN
    @PostMapping("/admin-block")
    public String blockAdmin(@RequestParam("adminId") Long id, @RequestParam("enabled") boolean enabled, RedirectAttributes redirectAttributes) {
        Admin admin = adminService.findById(id);
        if (admin == null) {
            redirectAttributes.addFlashAttribute("error", "Admin not found.");
            return "redirect:/mvc/admins-list";
        }
        User user = admin.getUser();
        user.setEnabled(enabled);
        userService.update(user);
        redirectAttributes.addFlashAttribute("success", "Admin has been blocked.");
        return "redirect:/mvc/admins-list";
    }

    // UNBLOCK AN ADMIN
    @PostMapping("/admin-unblock")
    public String unblockAdmin(@RequestParam("adminId") Long id, @RequestParam("enabled") boolean enabled, RedirectAttributes redirectAttributes) {
        Admin admin = adminService.findById(id);
        if (admin == null) {
            redirectAttributes.addFlashAttribute("error", "Admin not found.");
            return "redirect:/mvc/admins-list";
        }
        User user = admin.getUser();
        user.setEnabled(enabled);
        userService.update(user);
        redirectAttributes.addFlashAttribute("success", "Admin has been unblocked.");
        return "redirect:/mvc/admins-list";
    }

//
//    // READ ALL
//    @GetMapping("/admins")
//    public String findAll(Model model){
//        List<Admin> admins = adminService.findAll();
//        model.addAttribute("admins", admins);
//        return "restricted/admins";
//    }
//
//    // READ BY ID
//    @GetMapping("/admins/")
//    public String getAdminById(@RequestParam("adminId") Long id, Model model){
//        Admin admin = adminService.findById(id);
//        if(admin == null){
//            model.addAttribute("error", "Admin not found.");
//            return "restricted/admin-error";
//        }
//        model.addAttribute("admin", admin);
//        return "restricted/admin-info";
//    }
//
//    // CREATE - SHOW FORM
//    @GetMapping("/admin-form")
//    public String showAdminForm(Model model){
//        model.addAttribute("admin", new Admin());
//        return "restricted/admin-form";
//    }
//
//    // UPDATE - SHOW FORM
//    @GetMapping("/admin-form-update")
//    public String showUpdateAdminForm(@RequestParam("adminId") Long adminId, Model model){
//        Admin admin = adminService.findById(adminId);
//
//        model.addAttribute("admin", admin);
//        return "restricted/admin-form";
//    }
//
//    //  SAVE FORM
//    @PostMapping("/admin-form/create")
//    public String saveAdmin(@ModelAttribute("admin") Admin admin){
//
//        // PasswordEncoder
//        User tempUser = admin.getUser();
//        tempUser.setAdmin(admin);
//        admin.getUser().setPassword(passwordEncoder.encode(tempUser.getPassword()));
//        tempUser.setEnabled(true);
//        tempUser.setAuthority(new Authority(tempUser.getUsername(), admin.getRole()));
//        admin.setUser(tempUser);
//
//        // Save Admin
//        adminService.save(admin);
//        userService.update(tempUser);
//        return "redirect:/mvc/admins";
//    }
//
//    // UPDATE FORM
//    @PostMapping("/admin-form/update")
//    public String updateAdmin(@ModelAttribute("admin") Admin admin) {
//        Admin existingAdmin = adminService.findById(admin.getId());
//
//        if (existingAdmin == null) {
//            return "restricted/user-error";
//        }
//
//        // Update the user details
//        User tempUser = existingAdmin.getUser();
//        tempUser.setEmail(admin.getUser().getEmail());
//        tempUser.setUsername(admin.getUser().getUsername());
//
//        tempUser.setAuthority(new Authority(tempUser.getUsername(), admin.getRole()));
//        existingAdmin.setUser(tempUser);
//        existingAdmin.setRole(admin.getRole());
//
//        adminService.update(existingAdmin);
//        userService.update(tempUser);
//
//        return "redirect:/mvc/admins";
//    }
//
//
//    // DELETE BY ID
//    @GetMapping("/admin-delete")
//    public String deleteAdmin(@RequestParam("adminId") Long id){
//        adminService.deleteById(id);
//        return "redirect:/mvc/admins";
//    }

}