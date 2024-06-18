package com.dci.a3m.controller;

import com.dci.a3m.entity.*;
import com.dci.a3m.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class MemberControllerMVCTest {

    @Mock
    private MemberService memberService;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private FriendshipService friendshipService;

    @Mock
    private LikeService likeService;

    @Mock
    private PostService postService;

    @Mock
    private EmailService emailService;

    @Mock
    private Model model;

    @Mock
    private BindingResult result;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private MemberControllerMVC memberControllerMVC;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Other test cases...

    @Test
    void testChangePassword_memberFound() {
        Member member = new Member();
        when(memberService.findById(anyLong())).thenReturn(member);

        String view = memberControllerMVC.changePassword(1L, model);

        assertEquals("member-change-password", view);
        verify(model).addAttribute("member", member);
    }

    @Test
    void testChangePassword_memberNotFound() {
        when(memberService.findById(anyLong())).thenReturn(null);

        String view = memberControllerMVC.changePassword(1L, model);

        assertEquals("member-error", view);
        verify(model).addAttribute("error", "Member not found.");
    }

    @Test
    void testChangePasswordPost_success() {
        Member member = new Member();
        User user = new User();
        user.setPassword("$2a$10$H6SWcpZ3vVPs7obPpMcAQ.NnhGmQr1vRIj0wkcljVimLzqwgWY12q");
        member.setUser(user);

        when(memberService.findById(anyLong())).thenReturn(member);
        when(passwordEncoder.matches("oldPassword", user.getPassword())).thenReturn(true);
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");

        String view = memberControllerMVC.changePassword("oldPassword", "newPassword", "newPassword", 1L, redirectAttributes);

        assertEquals("redirect:/login-success", view);
        verify(memberService).update(member);
        verify(userService).update(user);
    }

    @Test
    void testChangePasswordPost_currentPasswordIncorrect() {
        Member member = new Member();
        User user = new User();
        user.setPassword("$2a$10$H6SWcpZ3vVPs7obPpMcAQ.NnhGmQr1vRIj0wkcljVimLzqwgWY12q");
        member.setUser(user);

        when(memberService.findById(anyLong())).thenReturn(member);
        when(passwordEncoder.matches("oldPassword", user.getPassword())).thenReturn(false);

        String view = memberControllerMVC.changePassword("oldPassword", "newPassword", "newPassword", 1L, redirectAttributes);

        assertEquals("redirect:/mvc/member-change-password?memberId=1", view);
        verify(redirectAttributes).addFlashAttribute("error", "Current Password is incorrect.");
    }

    @Test
    void testChangePasswordPost_newPasswordsDoNotMatch() {
        Member member = new Member();
        User user = new User();
        user.setPassword("$2a$10$H6SWcpZ3vVPs7obPpMcAQ.NnhGmQr1vRIj0wkcljVimLzqwgWY12q");
        member.setUser(user);

        when(memberService.findById(anyLong())).thenReturn(member);
        when(passwordEncoder.matches("oldPassword", user.getPassword())).thenReturn(true);

        String view = memberControllerMVC.changePassword("oldPassword", "newPassword", "differentNewPassword", 1L, redirectAttributes);

        assertEquals("redirect:/mvc/member-change-password?memberId=1", view);
        verify(redirectAttributes).addFlashAttribute("error", "New Passwords do no match.");
    }

    @Test
    void testChangePasswordPost_memberNotFound() {
        when(memberService.findById(anyLong())).thenReturn(null);

        String view = memberControllerMVC.changePassword("oldPassword", "newPassword", "newPassword", 1L, redirectAttributes);

        assertEquals("member-error", view);
    }
}
