package com.dci.a3m.controller;

import com.dci.a3m.entity.User;
import com.dci.a3m.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserControllerMVCTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private Model model;

    @InjectMocks
    private UserControllerMVC userControllerMVC;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        List<User> users = new ArrayList<>();
        when(userService.findAll()).thenReturn(users);

        String view = userControllerMVC.findAll(model);

        assertEquals("restricted/users", view);
        verify(model).addAttribute("users", users);
    }

    @Test
    void testGetUserById_userNotFound() {
        when(userService.findById(anyLong())).thenReturn(null);

        String view = userControllerMVC.getUserById(model, 1L);

        assertEquals("restricted/user-error", view);
        verify(model).addAttribute("error", "User not found.");
    }

    @Test
    void testGetUserById_userFound() {
        User user = new User();
        when(userService.findById(anyLong())).thenReturn(user);

        String view = userControllerMVC.getUserById(model, 1L);

        assertEquals("restricted/user-info", view);
        verify(model).addAttribute("user", user);
    }

    @Test
    void testRegisterUser() {
        String view = userControllerMVC.registerUser(model);

        assertEquals("restricted/user-form", view);
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(model).addAttribute(eq("user"), userCaptor.capture());
        assertNotNull(userCaptor.getValue());
    }

    @Test
    void testSaveUser() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");

        when(passwordEncoder.encode(anyString())).thenReturn("$2a$10$H6SWcpZ3vVPs7obPpMcAQ.NnhGmQr1vRIj0wkcljVimLzqwgWY12q");

        String view = userControllerMVC.saveUser(user);

        assertEquals("redirect:/mvc/users", view);
        verify(passwordEncoder).encode("testPassword");
        verify(userService).save(user);
        assertEquals("$2a$10$H6SWcpZ3vVPs7obPpMcAQ.NnhGmQr1vRIj0wkcljVimLzqwgWY12q", user.getPassword());
        assertTrue(user.isEnabled());
        assertEquals("ROLE_USER", user.getAuthority().getAuthority());
    }

    @Test
    void testDeleteUser() {
        String view = userControllerMVC.deleteUser(1L, model);

        assertEquals("redirect:/mvc/users", view);
        verify(userService).deleteById(1L);
    }
}
