package com.dci.a3m.controller;

import com.dci.a3m.entity.Admin;
import com.dci.a3m.entity.Member;
import com.dci.a3m.entity.User;
import com.dci.a3m.service.AdminService;
import com.dci.a3m.service.EmailService;
import com.dci.a3m.service.MemberService;
import com.dci.a3m.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class LoginControllerMVCTest {

    @Mock
    private MemberService memberService;

    @Mock
    private AdminService adminService;

    @Mock
    private UserService userService;

    @Mock
    private EmailService emailService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Model model;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private LoginControllerMVC loginControllerMVC;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() {
        String view = loginControllerMVC.login();
        assertEquals("login-form", view);
    }

    @Test
    void testLoginSuccess_memberRedirectsToProfileCompletion() {
        Member member = new Member();
        member.setId(1L);
        member.setFirstName(null);
        member.setLastName(null);
        when(memberService.getAuthenticatedMember()).thenReturn(member);
        when(adminService.getAuthenticatedAdmin()).thenReturn(null);

        String view = loginControllerMVC.loginSuccess();

        assertEquals("redirect:/mvc/members/?memberId=1", view);
    }

    @Test
    void testLoginSuccess_adminRedirectsToDashboard() {
        Admin admin = new Admin();
        when(memberService.getAuthenticatedMember()).thenReturn(null);
        when(adminService.getAuthenticatedAdmin()).thenReturn(admin);

        String view = loginControllerMVC.loginSuccess();

        assertEquals("redirect:/admin-dashboard/admin-dashboard", view);
    }

    @Test
    void testLoginSuccess_memberRedirectsToPostsOfFriends() {
        Member member = new Member();
        member.setFirstName("John");
        member.setLastName("Doe");
        when(memberService.getAuthenticatedMember()).thenReturn(member);
        when(adminService.getAuthenticatedAdmin()).thenReturn(null);

        String view = loginControllerMVC.loginSuccess();

        assertEquals("redirect:/mvc/posts-of-friends", view);
    }

    @Test
    void testLoggedCoderOrAdmin_member() {
        Member member = new Member();
        member.setId(1L);
        when(memberService.getAuthenticatedMember()).thenReturn(member);
        when(adminService.getAuthenticatedAdmin()).thenReturn(null);

        String view = loginControllerMVC.loggedCoderOrAdmin();

        assertEquals("redirect:/mvc/members/?memberId=1", view);
    }

    @Test
    void testLoggedCoderOrAdmin_admin() {
        Admin admin = new Admin();
        when(memberService.getAuthenticatedMember()).thenReturn(null);
        when(adminService.getAuthenticatedAdmin()).thenReturn(admin);

        String view = loginControllerMVC.loggedCoderOrAdmin();

        assertEquals("redirect:/admin-dashboard/admin-dashboard", view);
    }

    @Test
    void testForgotPassword_get() {
        String view = loginControllerMVC.forgotPassword();
        assertEquals("forgot-password", view);
    }

    @Test
    void testForgotPassword_post_memberFound() {
        Member member = new Member();
        User user = new User();
        user.setUsername("ThomasLake");
        member.setUser(user);
        when(memberService.findByEmail(anyString())).thenReturn(member);
        when(adminService.findByEmail(anyString())).thenReturn(null);

        String view = loginControllerMVC.forgotPassword("ThomasLake@example.com", "ThomasLake", model);

        assertEquals("forgot-password", view);
        verify(emailService).sendResetPasswordEmail(member);
        verify(model).addAttribute("message", "An email has been sent to ThomasLake@example.com with instructions to reset your password.");
    }

    @Test
    void testForgotPassword_post_adminFound() {
        Admin admin = new Admin();
        User user = new User();
        user.setUsername("ThomasLake");
        admin.setUser(user);
        when(memberService.findByEmail(anyString())).thenReturn(null);
        when(adminService.findByEmail(anyString())).thenReturn(admin);

        String view = loginControllerMVC.forgotPassword("ThomasLake@example.com", "ThomasLake", model);

        assertEquals("forgot-password", view);
        // emailService.sendResetPasswordEmail(admin);
        verify(model).addAttribute("message", "An email has been sent to ThomasLake@example.com with instructions to reset your password.");
    }

    @Test
    void testForgotPassword_post_noAccountFound() {
        when(memberService.findByEmail(anyString())).thenReturn(null);
        when(adminService.findByEmail(anyString())).thenReturn(null);

        String view = loginControllerMVC.forgotPassword("ThomasLake@example.com", "ThomasLake", model);

        assertEquals("forgot-password", view);
        verify(model).addAttribute("message", "The email and username do not match.");
    }

    @Test
    void testShowResetPasswordForm() {
        String view = loginControllerMVC.showResetPasswordForm("ThomasLake@example.com", model);
        assertEquals("reset-password", view);
        verify(model).addAttribute("email", "ThomasLake@example.com");
    }

    @Test
    void testResetPassword_passwordsDoNotMatch() {
        String view = loginControllerMVC.resetPassword("ThomasLake@example.com", "password", "differentPassword", model);
        assertEquals("reset-password", view);
        verify(model).addAttribute("message", "Passwords do not match.");
    }

    @Test
    void testResetPassword_memberNotFound() {
        when(memberService.findByEmail(anyString())).thenReturn(null);

        String view = loginControllerMVC.resetPassword("ThomasLake@example.com", "password", "password", model);
        assertEquals("reset-password", view);
        verify(model).addAttribute("message", "Invalid email.");
    }

    @Test
    void testResetPassword_success() {
        Member member = new Member();
        User user = new User();
        member.setUser(user);
        when(memberService.findByEmail(anyString())).thenReturn(member);

        String view = loginControllerMVC.resetPassword("ThomasLake@example.com", "password", "password", model);
        assertEquals("login-form", view);
        verify(memberService).save(member);
        verify(model).addAttribute("message", "Your password has been reset successfully.");
    }

}

