package com.dci.a3m.controller;

import com.dci.a3m.entity.FriendshipInvitation;
import com.dci.a3m.entity.Member;
import com.dci.a3m.service.FriendshipService;
import com.dci.a3m.service.MemberService;
import com.dci.a3m.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.ArrayList;

import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class FriendshipControllerMVCTest {

    @Mock
    private FriendshipService friendshipService;

    @Mock
    private MemberService memberService;

    @Mock
    private PostService postService;

    @Mock
    private Model model;

    @InjectMocks
    private FriendshipControllerMVC friendshipControllerMVC;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testShowFriendshipInvitations() {
        Member member = new Member();
        List<FriendshipInvitation> invitations = new ArrayList<>();
        when(memberService.getAuthenticatedMember()).thenReturn(member);
        when(friendshipService.findByAcceptingMemberAndNotAccepted(member)).thenReturn(invitations);

        String view = friendshipControllerMVC.showFriendshipInvitations(model);

        assertEquals("friendship-invitations", view);
        verify(model).addAttribute("invitations", invitations);
    }

    @Test
    void testShowFriends() {
        Member member = new Member();
        List<FriendshipInvitation> friendsAcceptedAndNotAccepted = new ArrayList<>();
        when(memberService.getAuthenticatedMember()).thenReturn(member);
        when(friendshipService.findFriendsAccepted(member)).thenReturn(friendsAcceptedAndNotAccepted);

        String view = friendshipControllerMVC.showFriends(model);

        assertEquals("friends", view);
        verify(model).addAttribute(eq("friendDetails"), any(List.class));
    }

    @Test
    void testSendFriendshipInvitation() {
        Member invitingMember = new Member();
        Member acceptingMember = new Member();
        when(memberService.getAuthenticatedMember()).thenReturn(invitingMember);
        when(memberService.findById(anyLong())).thenReturn(acceptingMember);

        String view = friendshipControllerMVC.sendFriendshipInvitation(1L);

        assertEquals("redirect:/mvc/members", view);
        verify(friendshipService).createFriendshipInvitation(invitingMember, acceptingMember);
    }

    @Test
    void testAcceptFriendshipInvitation() {
        String view = friendshipControllerMVC.acceptFriendshipInvitation(1L);

        assertEquals("redirect:/mvc/friends", view);
        verify(friendshipService).acceptFriendshipInvitation(1L);
    }

    @Test
    void testDeclineFriendshipInvitation() {
        String view = friendshipControllerMVC.declineFriendshipInvitation(1L);

        assertEquals("redirect:/mvc/members", view);
        verify(friendshipService).declineFriendshipInvitation(1L);
    }

    @Test
    void testRemoveFriendshipInvitation() {
        String view = friendshipControllerMVC.removeFriendshipInvitation(1L);

        assertEquals("redirect:/mvc/members", view);
        verify(friendshipService).declineFriendshipInvitation(1L);
    }
}

