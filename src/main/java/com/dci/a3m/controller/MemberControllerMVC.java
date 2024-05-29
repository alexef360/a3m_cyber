package com.dci.a3m.controller;


import com.dci.a3m.entity.Authority;
import com.dci.a3m.entity.Member;
import com.dci.a3m.entity.User;
import com.dci.a3m.service.MemberService;
import com.dci.a3m.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mvc")
public class MemberControllerMVC {

    MemberService memberService;
    UserService userService;
    PasswordEncoder passwordEncoder;

    @Autowired
    public MemberControllerMVC(MemberService memberService, UserService userService, PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // CRUD OPERATIONS

    // READ ALL
    @GetMapping("/members")
    public String findAll(Model model) {
        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);
        return "members";
    }

    // READ BY ID
    @GetMapping("/members/")
    public String getMemberById(@RequestParam("memberId") Long id, Model model) {
        Member member = memberService.findById(id);
        if (member == null) {
            model.addAttribute("error", "Member not found.");
            return "member-error";
        }
        model.addAttribute("member", member);
        return "member-info";
    }

    // CREATE - SHOW FORM
    @GetMapping("/member-form")
    public String showMemberForm(Model model) {
        model.addAttribute("member", new Member());
        return "member-form";
    }

    // UPDATE - SHOW FORM
    @GetMapping("/member-form-update")
    public String showMemberFormUpdate(@RequestParam("memberId") Long id, Model model) {
        Member member = memberService.findById(id);
        model.addAttribute("member", member);
        return "member-form";
    }

    // SAVE FORM
    @PostMapping("/member-form/create")
public String saveMember(@ModelAttribute("member") Member member) {

        // PasswordEncoder
        User tempUser = member.getUser();
        tempUser.setMember(member);
        member.getUser().setPassword(passwordEncoder.encode(tempUser.getPassword()));
        tempUser.setEnabled(true);
        tempUser.setAuthority(new Authority(tempUser.getUsername(), member.getRole()));


        member.setUser(tempUser);

        // Save Member
        memberService.save(member);
        userService.update(tempUser);
        return "redirect:/mvc/members";
    }

    // UPDATE FORM
    @PostMapping("/member-form/update")
    public String updateMember(@ModelAttribute("member") Member member) {
        Member existingMember = memberService.findById(member.getId());

        if (existingMember == null) {
            return "user-error";
        }

        // Update the user details
        User tempUser = existingMember.getUser();
        tempUser.setEmail(member.getUser().getEmail());
        tempUser.setUsername(member.getUser().getUsername());

        tempUser.setAuthority(new Authority(tempUser.getUsername(), member.getRole()));
        existingMember.setUser(tempUser);
        existingMember.setRole(member.getRole());

        existingMember.setFirstName(member.getFirstName());
        existingMember.setLastName(member.getLastName());
        existingMember.setBirthDate(member.getBirthDate());
        existingMember.setProfilePicture(member.getProfilePicture());
        existingMember.setCity(member.getCity());
        existingMember.setCountry(member.getCountry());
        existingMember.setPostalCode(member.getPostalCode());
        existingMember.setPhone(member.getPhone());

        memberService.update(existingMember);
        userService.update(tempUser);

        return "redirect:/mvc/members";
    }


    // DELETE
    @GetMapping("/member-delete")
    public String deleteMember(@RequestParam("memberId") Long id) {
        memberService.deleteById(id);
        return "redirect:/mvc/members";
    }


}
