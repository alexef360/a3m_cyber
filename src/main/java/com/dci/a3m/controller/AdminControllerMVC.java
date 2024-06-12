package com.dci.a3m.controller;

import com.dci.a3m.entity.*;
import com.dci.a3m.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin-dashboard")
public class AdminControllerMVC {

    private final AdminService adminService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final MemberService memberService;
    private final PostService postService;
    private final CommentService commentService;

    @Autowired
    public AdminControllerMVC(PasswordEncoder passwordEncoder, AdminService adminService, UserService userService, MemberService memberService, PostService postService, CommentService commentService) {
        this.passwordEncoder = passwordEncoder;
        this.adminService = adminService;
        this.userService = userService;
        this.memberService = memberService;
        this.postService = postService;
        this.commentService = commentService;
    }

    @GetMapping("/admin-dashboard")
    public String adminDashboard() {
        return "restricted/admin-dashboard";
    }

    @GetMapping("/members-list")
    public String membersList(Model model) {
        List<Member> members = memberService.findAll();
        model.addAttribute("members", members);
        return "restricted/members-list";
    }

    @PostMapping("/searchUsername")
    public String searchUsername(@RequestParam("memberUsername") String username, Model model, RedirectAttributes redirectAttributes) {
        Member member = memberService.findByUsername(username);
        String memberUsername = member.getUser().getUsername();
        if (!memberUsername.equals(username)) {
            redirectAttributes.addFlashAttribute("error", "Member not found.");
            return "redirect:/admin-dashboard/members-list";
        }
        model.addAttribute("member", member);
        return "restricted/member-details";
    }



    @PostMapping("/member-block")
    public String blockMember(@RequestParam("memberId") Long id, @RequestParam("enabled") boolean enabled, RedirectAttributes redirectAttributes) {
        Member member = memberService.findById(id);
        if (member == null) {
            redirectAttributes.addFlashAttribute("error", "Member not found.");
            return "redirect:/admin-dashboard/members-list";
        }
        User user = member.getUser();
        user.setEnabled(enabled);
        userService.update(user);
        redirectAttributes.addFlashAttribute("success", "Member has been blocked.");
        return "redirect:/admin-dashboard/members-list";
    }

    @PostMapping("/member-unblock")
    public String unblockMember(@RequestParam("memberId") Long id, @RequestParam("enabled") boolean enabled, RedirectAttributes redirectAttributes) {
        Member member = memberService.findById(id);
        if (member == null) {
            redirectAttributes.addFlashAttribute("error", "Member not found.");
            return "redirect:/admin-dashboard/members-list";
        }
        User user = member.getUser();
        user.setEnabled(enabled);
        userService.update(user);
        redirectAttributes.addFlashAttribute("success", "Member has been unblocked.");
        return "redirect:/admin-dashboard/members-list";
    }

    @PostMapping("/member-delete")
    public String deleteMember(@RequestParam("memberId") Long id, RedirectAttributes redirectAttributes) {
        Member member = memberService.findById(id);
        if (member == null) {
            redirectAttributes.addFlashAttribute("error", "Member not found.");
            return "redirect:/admin-dashboard/members-list";
        }
        memberService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Member has been deleted.");
        return "redirect:/admin-dashboard/members-list";
    }

    @PostMapping("/members-create-init")
    public String createInitMembers(RedirectAttributes redirectAttributes) {
        if (!memberService.findAll().isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Members already exist.");
            return "redirect:/admin-dashboard/members-list";
        }
        memberService.createInitMembers();
        redirectAttributes.addFlashAttribute("success", "Initial members have been created.");
        return "redirect:/admin-dashboard/members-list";
    }

    @PostMapping("/members-delete-all")
    public String deleteAllMembers(RedirectAttributes redirectAttributes) {
        memberService.deleteAll();
        redirectAttributes.addFlashAttribute("success", "All members have been deleted.");
        return "redirect:/admin-dashboard/members-list";
    }

    @GetMapping("/posts-list")
    public String postsList(Model model) {
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        return "restricted/posts-list";
    }

    @PostMapping("/post-delete")
    public String deletePost(@RequestParam("postId") Long id, RedirectAttributes redirectAttributes) {
        Post post = postService.findById(id);
        if (post == null) {
            redirectAttributes.addFlashAttribute("error", "Post not found.");
            return "redirect:/admin-dashboard/posts-list";
        }
        postService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Post has been deleted.");
        return "redirect:/admin-dashboard/posts-list";
    }

    @GetMapping("/comments-list")
    public String commentsList(Model model) {
        List<Comment> comments = commentService.findAll();
        model.addAttribute("comments", comments);
        return "restricted/comments-list";
    }

    @PostMapping("/comment-delete")
    public String deleteComment(@RequestParam("commentId") Long id, RedirectAttributes redirectAttributes) {
        Comment comment = commentService.findById(id);
        if (comment == null) {
            redirectAttributes.addFlashAttribute("error", "Comment not found.");
            return "redirect:/admin-dashboard/comments-list";
        }
        commentService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Comment has been deleted.");
        return "redirect:/admin-dashboard/comments-list";
    }
}
