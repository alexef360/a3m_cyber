package com.dci.a3m.controller;


import com.dci.a3m.entity.Comment;
import com.dci.a3m.entity.Member;
import com.dci.a3m.entity.Post;
import com.dci.a3m.entity.User;
import com.dci.a3m.service.CommentService;
import com.dci.a3m.service.MemberService;
import com.dci.a3m.service.PostService;
import com.dci.a3m.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mvc")
public class CommentControllerMVC {
    private final UserDetailsManager userDetailsManager;
    private  CommentService commentService;
    private  MemberService memberService;
    private PostService postService;
    private UserService userService;

    @Autowired
    public CommentControllerMVC(UserDetailsManager userDetailsManager, CommentService commentService, MemberService memberService, PostService postService, UserService userService) {
        this.userDetailsManager = userDetailsManager;
        this.commentService = commentService;
        this.memberService = memberService;
        this.postService = postService;
        this.userService = userService;
    }
    // CRUD OPERATIONS

    // READ ALL
    @GetMapping("/comments")
    public String findAll(Model model) {
        List<Comment> comments = commentService.findAll();
        model.addAttribute("comments", comments);
        return "comments";
    }

    // READ ALL COMMENTS OF CURRENTLY LOGGED IN MEMBER
    @GetMapping("/comments/member")
    public String findAllByMember(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        Member member = user.getMember();

        if (member == null) {
            model.addAttribute("error", "Member not found.");
            return "member-error";
        }

        List<Comment> comments = commentService.findAllByMember(member);
        model.addAttribute("comments", comments);
        return "comments";
    }

    // READ BY ID
    @GetMapping("/comments/")
    public String getCommentById(@RequestParam("commentId") Long id, Model model) {
        Comment comment = commentService.findById(id);
        if (comment == null) {
            model.addAttribute("error", "Comment not found.");
            return "comment-error";
        }
        model.addAttribute("comment", comment);
        return "comment-info";
    }

    // CREATE COMMENT - SHOW FORM
    @GetMapping("/comment-form")
    public String showCommentForm(@RequestParam("postId") Long postId, Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        Member member = user.getMember();
        if (member == null) {
            model.addAttribute("error", "Member not found.");
            return "member-error";
        }
        model.addAttribute("postId", postId);
        model.addAttribute("comment", new Comment());
        return "comment-form";
    }

    // UPDATE COMMENT - SHOW FORM
    @GetMapping("/comment-form-update")
    public String showCommentFormUpdate(@RequestParam("commentId") Long id, Model model) {
        Comment comment = commentService.findById(id);
        if (comment == null) {
            model.addAttribute("error", "Comment not found.");
            return "comment-error";
        }
        model.addAttribute("comment", comment);
        return "comment-form";
    }

    // SAVE COMMENT
    @PostMapping("/comment-form/create")
    public String saveComment(@RequestParam("postId") Long postId, Model model, Comment comment) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        Member member = user.getMember();
        if (member == null) {
            return "comment-error";
        }
        Post post = postService.findById(postId);
        comment.setPost(post);
        comment.setMember(member);
        commentService.save(comment);
        return "redirect:/mvc/posts/?postId=" + postId;
    }

    // UPDATE COMMENT
    @PostMapping("/comment-form/update")
    public String updateComment(@ModelAttribute("comment") Comment comment) {
        Comment existingComment = commentService.findById(comment.getId());
        if (existingComment == null) {
            return "comment-error";
        }
        existingComment.setContent(comment.getContent());
        commentService.save(existingComment);
        return "redirect:/mvc/posts/?postId=" + existingComment.getPost().getId();
    }

    // DELETE COMMENT
    @GetMapping("/comment-delete")
    public String deleteComment(@RequestParam("commentId") Long id) {
        Comment comment = commentService.findById(id);
        commentService.deleteById(id);
        return "redirect:/mvc/posts/?postId=" + comment.getPost().getId();
    }
}