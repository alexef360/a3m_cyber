package com.dci.a3m.controller;

import com.dci.a3m.entity.Member;
import com.dci.a3m.entity.Post;
import com.dci.a3m.entity.User;
import com.dci.a3m.service.MemberService;
import com.dci.a3m.service.PostService;
import com.dci.a3m.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/mvc")
public class PostControllerMVC {

    private final PostService postService;
    private final MemberService memberService;
    private final UserService userService;

    @Autowired
    public PostControllerMVC(PostService postService, MemberService memberService, UserService userService) {
        this.postService = postService;
        this.memberService = memberService;
        this.userService = userService;
    }

    // CRUD OPERATIONS

    // READ ALL
    @GetMapping("/posts")
    public String findAll(Model model) {
        List<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        return "posts";
    }

    // READ ALL POST OF CURRENTLY LOGGED IN MEMBER
    @GetMapping("/posts/member")
    public String findAllByMember(Model model) {
        // Currently logged in member
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        Member member = user.getMember();

        if (member == null) {
            model.addAttribute("error", "Member not found.");
            return "member-error";
        }

        List<Post> posts = postService.findAllByMember(member);
        model.addAttribute("posts", posts);
        return "posts";
    }

    // READ BY ID
    @GetMapping("/posts/")
    public String getPostById(@RequestParam("postId") Long id, Model model) {
        Post post = postService.findById(id);
        if (post == null) {
            model.addAttribute("error", "Post not found.");
            return "post-error";
        }
        model.addAttribute("post", post);
        return "post-info";
    }


    // CREATE POST - SHOW FORM
    @GetMapping("/post-form")
    public String showPostForm(Model model) {
        // show the form only to currently logged in member
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        Member member = user.getMember();
        if (member == null) {
            model.addAttribute("error", "Member not found.");
            return "member-error";
        }

        model.addAttribute("post", new Post());
        return "post-form";
    }

    // UPDATE POST - SHOW FORM
    @GetMapping("/post-form-update")
    public String showPostFormUpdate(@RequestParam("postId") Long id, Model model) {
        Post post = postService.findById(id);
        if (post == null) {
            model.addAttribute("error", "Post not found.");
            return "post-error";
        }
        model.addAttribute("post", post);
        return "post-form";
    }


    // SAVE POST
    @PostMapping("/post-form/create")
    public String savePost(@ModelAttribute("post") Post post) {
        // Currently logged in member

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        Member member = user.getMember();
        if (member == null) {
            return "member-error";
        }

        post.setMember(member);
        postService.save(post);
        return "redirect:/mvc/posts";
    }

    // UPDATE POST
    @PostMapping("/post-form/update")
    public String updatePost(@ModelAttribute("post") Post post) {

        Post existingPost = postService.findById(post.getId());

        existingPost.setContent(post.getContent());
        existingPost.setBackground(post.getBackground());
        existingPost.setMediaUrl(post.getMediaUrl());

        postService.save(existingPost);
        return "redirect:/mvc/posts";
    }

    // DELETE POST
    @GetMapping("/post-delete")
    public String deletePost(@RequestParam("postId") Long id) {
        postService.deleteById(id);
        return "redirect:/mvc/posts";
    }

}
