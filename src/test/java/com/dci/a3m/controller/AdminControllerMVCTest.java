package com.dci.a3m.controller;

import com.dci.a3m.entity.*;
import com.dci.a3m.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AdminControllerMVCTest {

    @Mock
    private AdminService adminService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserService userService;

    @Mock
    private MemberService memberService;

    @Mock
    private PostService postService;

    @Mock
    private CommentService commentService;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private AdminControllerMVC adminControllerMVC;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAdminDashboard() {
        String view = adminControllerMVC.adminDashboard();
        assertEquals("restricted/admin-dashboard", view);
    }

    @Test
    void testMembersList() {
        List<Member> members = List.of(new Member());
        when(memberService.findAll()).thenReturn(members);

        String view = adminControllerMVC.membersList(model);

        assertEquals("restricted/members-list", view);
        verify(model).addAttribute("members", members);
    }

    @Test
    void testSearchUsername_memberFound() {
        Member member = new Member();
        when(memberService.findByUsername(anyString())).thenReturn(member);

        String view = adminControllerMVC.searchUsername("username", model, redirectAttributes);

        assertEquals("restricted/members-list", view);
        verify(model).addAttribute("members", List.of(member));
        verify(model).addAttribute("success", "Member founded");
        verifyNoInteractions(redirectAttributes);
    }

    @Test
    void testSearchUsername_memberNotFound() {
        when(memberService.findByUsername(anyString())).thenReturn(null);

        String view = adminControllerMVC.searchUsername("username", model, redirectAttributes);

        assertEquals("redirect:/admin-dashboard/members-list", view);
        verify(redirectAttributes).addFlashAttribute("error", "Member not found.");
        verifyNoInteractions(model);
    }

    @Test
    void testBlockMember_memberFound() {
        Member member = new Member();
        User user = new User();
        member.setUser(user);
        when(memberService.findById(anyLong())).thenReturn(member);

        String view = adminControllerMVC.blockMember(1L, false, redirectAttributes);

        assertEquals("redirect:/admin-dashboard/members-list", view);
        assertFalse(user.isEnabled());
        verify(userService).update(user);
        verify(redirectAttributes).addFlashAttribute("success", "Member has been blocked.");
    }

    @Test
    void testBlockMember_memberNotFound() {
        when(memberService.findById(anyLong())).thenReturn(null);

        String view = adminControllerMVC.blockMember(1L, false, redirectAttributes);

        assertEquals("redirect:/admin-dashboard/members-list", view);
        verify(redirectAttributes).addFlashAttribute("error", "Member not found.");
        verifyNoInteractions(userService);
    }

    @Test
    void testDeleteMember_memberFound() {
        Member member = new Member();
        when(memberService.findById(anyLong())).thenReturn(member);

        String view = adminControllerMVC.deleteMember(1L, redirectAttributes);

        assertEquals("redirect:/admin-dashboard/members-list", view);
        verify(memberService).deleteById(1L);
        verify(redirectAttributes).addFlashAttribute("success", "Member has been deleted.");
    }

    @Test
    void testDeleteMember_memberNotFound() {
        when(memberService.findById(anyLong())).thenReturn(null);

        String view = adminControllerMVC.deleteMember(1L, redirectAttributes);

        assertEquals("redirect:/admin-dashboard/members-list", view);
        verify(redirectAttributes).addFlashAttribute("error", "Member not found.");
        verify(memberService, never()).deleteById(anyLong());
    }
}
