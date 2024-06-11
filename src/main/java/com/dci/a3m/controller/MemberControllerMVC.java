package com.dci.a3m.controller;


import com.dci.a3m.entity.*;
import com.dci.a3m.service.FriendshipService;
import com.dci.a3m.service.LikeService;
import com.dci.a3m.service.MemberService;
import com.dci.a3m.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/mvc")
public class MemberControllerMVC {

    MemberService memberService;
    UserService userService;
    PasswordEncoder passwordEncoder;
    AuthenticationManager authenticationManager;
    FriendshipService friendshipService;
    LikeService likeService;

    @Autowired
    public MemberControllerMVC(MemberService memberService, UserService userService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, FriendshipService friendshipService, LikeService likeService) {
        this.memberService = memberService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.friendshipService = friendshipService;
        this.likeService = likeService;
    }

    // CRUD OPERATIONS

    // READ ALL
    @GetMapping("/members")
    public String findAll(Model model) {
        // Get authenticated member
        Member authenticatedMember = memberService.getAuthenticatedMember();
        List<Member> members = memberService.findAll().stream()
                .filter(member -> !member.getId().equals(authenticatedMember.getId()))
                .collect(Collectors.toList());

        // Prepare attributes with friends accepted for Thymeleaf
        List<FriendshipInvitation> friends = friendshipService.findFriendsAccepted(authenticatedMember);
        List<Long> friendIds = friends.stream()
                .map(friend -> friend.getInvitingMember().getId().equals(authenticatedMember.getId()) ? friend.getAcceptingMember().getId() : friend.getInvitingMember().getId())
                .collect(Collectors.toList());

        // Prepare attributes with pending friendship invitations for Thymeleaf
        List<FriendshipInvitation> pendingReceivedInvitations = friendshipService.findByAcceptingMemberAndNotAccepted(authenticatedMember);
        List<Long> pendingReceivedIds = pendingReceivedInvitations.stream()
                .map(friend -> friend.getInvitingMember().getId())
                .collect(Collectors.toList());

        List<FriendshipInvitation> pendingSentInvitations = friendshipService.findByInvitingMemberAndNotAccepted(authenticatedMember);
        List<Long> pendingSentIds = pendingSentInvitations.stream()
                .map(friend -> friend.getAcceptingMember().getId())
                .collect(Collectors.toList());

        List<FriendshipInvitation> invitations = friendshipService.findByAcceptingMemberAndNotAccepted(authenticatedMember);
        model.addAttribute("invitations", invitations);


        model.addAttribute("members", members);
        model.addAttribute("friendIds", friendIds);
        model.addAttribute("pendingSentIds", pendingSentIds);
        model.addAttribute("pendingReceivedIds", pendingReceivedIds);

        return "members";
    }

    // READ BY ID
    @GetMapping("/members/")
    public String getMemberById(@RequestParam("memberId") Long id, Model model) {
        Member authenticatedMember = memberService.findById(id);
        if (authenticatedMember == null) {
            model.addAttribute("error", "Member not found.");
            return "member-error";
        }

        // Prepare Posts attributes for Thymeleaf
        List<Post> posts = authenticatedMember.getPosts();
        Map<Long, Boolean> likedYourPosts = new HashMap<>();
        for (Post post : posts) {
            boolean liked = likeService.hasMemberLikedPost(authenticatedMember, post);
            likedYourPosts.put(post.getId(), liked);
        }
        model.addAttribute("posts", posts);
        model.addAttribute("member", authenticatedMember);
        model.addAttribute("likedYourPosts", likedYourPosts);


        // Prepare Friends attributes for Thymeleaf
        List<FriendshipInvitation> friends = friendshipService.findFriendsAccepted(authenticatedMember);
        List<Map<String, Object>> friendDetails = friends.stream().map(friend -> {
            Map<String, Object> details = new HashMap<>();

            details.put("friendshipId", friend.getId());

            if (friend.getInvitingMember().getId().equals(authenticatedMember.getId())) {
                details.put("profilePicture", friend.getAcceptingMember().getProfilePicture());
                details.put("firstName", friend.getAcceptingMember().getFirstName());
                details.put("lastName", friend.getAcceptingMember().getLastName());
                details.put("username", friend.getAcceptingMember().getUser().getUsername());
                details.put("memberId", friend.getAcceptingMember().getId());
            } else {
                details.put("profilePicture", friend.getInvitingMember().getProfilePicture());
                details.put("firstName", friend.getInvitingMember().getFirstName());
                details.put("lastName", friend.getInvitingMember().getLastName());
                details.put("username", friend.getInvitingMember().getUser().getUsername());
                details.put("memberId", friend.getInvitingMember().getId());
            }
            return details;
        }).collect(Collectors.toList());
        model.addAttribute("friendDetails", friendDetails);

        // Friendship Invitations
        List<FriendshipInvitation> invitations = friendshipService.findByAcceptingMemberAndNotAccepted(authenticatedMember);
        model.addAttribute("invitations", invitations);

        return "member-info";
    }

    // READ BY USERNAME
    @GetMapping("/members/username")
    public String getMemberByUsername(@RequestParam("username") String username, Model model) {
        User user = userService.findByUsername(username);
        Member member = user.getMember();
        if (member == null) {
            model.addAttribute("error", "Member not found.");
            return "member-error";
        }
        model.addAttribute("", member);
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
    public String showMemberFormUpdate(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        Member member = user.getMember();
        if (member == null) {
            model.addAttribute("error", "Member not found.");
            return "member-error";
        }
        member.setUser(user);
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

        // after creating a new member log in the saved member automatically
//        UsernamePasswordAuthenticationToken authReq
//                = new UsernamePasswordAuthenticationToken(tempUser.getUsername(), tempUser.getPassword());
//        Authentication auth = authenticationManager.authenticate(authReq);
//        SecurityContext sc = SecurityContextHolder.getContext();
//        sc.setAuthentication(auth);

        // Authenticate the saved member
//        UserDetails userDetails = userService.loadUserByUsername(tempUser.getUsername());
//        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(authentication);

        member = memberService.getAuthenticatedMember();

        return "redirect:/mvc/members";
    }

    // UPDATE FORM
    @PostMapping("/member-form/update")
    public String updateMember(@ModelAttribute("member") Member member) {
        Member existingMember = memberService.findById(member.getId());

        if (existingMember == null) {
            return "member-error";
        }

        // Update the user details
        User tempUser = existingMember.getUser();
        tempUser.setEmail(member.getUser().getEmail());
        tempUser.setUsername(member.getUser().getUsername());
       // tempUser.setPassword(passwordEncoder.encode(member.getUser().getPassword()));

        tempUser.setAuthority(new Authority(tempUser.getUsername(), member.getRole()));
        existingMember.setUser(tempUser);
        existingMember.setRole(member.getRole());

        existingMember.setFirstName(member.getFirstName());
        existingMember.setLastName(member.getLastName());
        //existingMember.setFormattedBirthDate(member.getFormattedBirthDate());
        existingMember.setProfilePicture(member.getProfilePicture());
        existingMember.setCity(member.getCity());
        existingMember.setCountry(member.getCountry());
        existingMember.setPostalCode(member.getPostalCode());
        existingMember.setPhone(member.getPhone());

        memberService.update(existingMember);
        userService.update(tempUser);

        return "redirect:/login-success";
    }


    // DELETE
    @GetMapping("/member-delete")
    public String deleteMember(@RequestParam("memberId") Long id) {
        memberService.deleteById(id);
        return "redirect:/login-form?logout";
    }

    //CHANGE PASSWORD
    @GetMapping("/member-change-password")
    public String changePassword(@RequestParam("memberId") Long id, Model model) {
        Member member = memberService.findById(id);
        if (member == null) {
            model.addAttribute("error", "Member not found.");
            return "member-error";
        }
        model.addAttribute("member", member);
        return "member-change-password";
    }

    @PostMapping("/member-change-password")
    public String changePassword(@RequestParam("currentPassword") String currentPassword,
                                 @RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmNewPassword") String confirmNewPassword,
                                 @RequestParam("memberId") Long id, RedirectAttributes redirectAttributes) {
        Member member = memberService.findById(id);
        if (member == null) {
            return "member-error";
        }
        User user = member.getUser();
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            redirectAttributes.addFlashAttribute("error", "Current Password is incorrect.");
            return "redirect:/mvc/member-change-password?memberId=" + id;
        }
        if (!newPassword.equals(confirmNewPassword)) {
            redirectAttributes.addFlashAttribute("error", "New Passwords do no match.");
            return "redirect:/mvc/member-change-password?memberId=" + id;
        }
        else {
            user.setPassword(passwordEncoder.encode(newPassword));
            memberService.update(member);
            userService.update(user);
            return "redirect:/login-success";
        }
    }

}
