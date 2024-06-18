package com.dci.a3m.controller;

import com.dci.a3m.entity.*;
import com.dci.a3m.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PostControllerMVCTest {

    @Mock
    private PostService postService;

    @Mock
    private MemberService memberService;

    @Mock
    private UserService userService;

    @Mock
    private LikeService likeService;

    @Mock
    private CommentService commentService;

    @Mock
    private FriendshipService friendshipService;

    @Mock
    private BadWordsFilterService badWordsFilterService;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private PostControllerMVC postControllerMVC;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll_memberNotFound() {
        when(memberService.getAuthenticatedMember()).thenReturn(null);

        String view = postControllerMVC.findAll(model);

        assertEquals("member-error", view);
        verify(model).addAttribute("error", "Member not found.");
    }

    @Test
    void testFindAll_memberFound() {
        Member member = new Member();
        member.setId(1L);

        List<FriendshipInvitation> friends = new ArrayList<>();
        List<Post> posts = new ArrayList<>();

        when(memberService.getAuthenticatedMember()).thenReturn(member);
        when(friendshipService.findFriendsAccepted(member)).thenReturn(friends);
        when(postService.findAll()).thenReturn(posts);

        String view = postControllerMVC.findAll(model);

        assertEquals("posts-of-friends", view);
        verify(model).addAttribute("posts", posts);
    }

    @Test
    void testFindAllYour_memberNotFound() {
        when(memberService.getAuthenticatedMember()).thenReturn(null);

        String view = postControllerMVC.findAllYour(model);

        assertEquals("member-error", view);
        verify(model).addAttribute("error", "Member not found.");
    }

    @Test
    void testFindAllYour_memberFound() {
        Member member = new Member();
        member.setId(1L);
        List<Post> myPosts = new ArrayList<>();

        member.setPosts(myPosts);

        when(memberService.getAuthenticatedMember()).thenReturn(member);

        String view = postControllerMVC.findAllYour(model);

        assertEquals("posts-your", view);
        verify(model).addAttribute("myPosts", myPosts);
    }

    @Test
    void testFindAllByMember_memberNotFound() {
        when(memberService.getAuthenticatedMember()).thenReturn(null);

        String view = postControllerMVC.findAllByMember(model);

        assertEquals("member-error", view);
        verify(model).addAttribute("error", "Member not found.");
    }

    @Test
    void testFindAllByMember_memberFound() {
        Member member = new Member();
        member.setId(1L);
        List<Post> posts = new ArrayList<>();

        when(memberService.getAuthenticatedMember()).thenReturn(member);
        when(postService.findAllByMember(member)).thenReturn(posts);

        String view = postControllerMVC.findAllByMember(model);

        assertEquals("posts-your", view);
        verify(model).addAttribute("posts", posts);
    }

    @Test
    void testGetPostById_postNotFound() {
        when(postService.findById(anyLong())).thenReturn(null);

        String view = postControllerMVC.getPostById(1L, model);

        assertEquals("post-error", view);
        verify(model).addAttribute("error", "Post not found.");
    }

    @Test
    void testGetPostById_postFound() {
        Member member = new Member();
        member.setId(1L);
        Post post = new Post();
        post.setId(1L);
        post.setMember(member);
        List<Comment> comments = new ArrayList<>();

        post.setComments(comments);

        when(memberService.getAuthenticatedMember()).thenReturn(member);
        when(postService.findById(anyLong())).thenReturn(post);
        when(likeService.hasMemberLikedPost(member, post)).thenReturn(true);

        String view = postControllerMVC.getPostById(1L, model);

        assertEquals("post-info", view);
        verify(model).addAttribute("post", post);
    }

    @Test
    void testShowPostForm_memberNotFound() {
        when(memberService.getAuthenticatedMember()).thenReturn(null);

        String view = postControllerMVC.showPostForm(model);

        assertEquals("member-error", view);
        verify(model).addAttribute("error", "Member not found.");
    }

    @Test
    void testShowPostForm_memberFound() {
        Member member = new Member();
        member.setId(1L);

        when(memberService.getAuthenticatedMember()).thenReturn(member);

        String view = postControllerMVC.showPostForm(model);

        assertEquals("post-form", view);
        ArgumentCaptor<Post> postCaptor = ArgumentCaptor.forClass(Post.class);
        verify(model).addAttribute(eq("post"), postCaptor.capture());
        assertNotNull(postCaptor.getValue());
    }

    @Test
    void testShowPostFormUpdate_postNotFound() {
        when(postService.findById(anyLong())).thenReturn(null);

        String view = postControllerMVC.showPostFormUpdate(1L, model);

        assertEquals("post-error", view);
        verify(model).addAttribute("error", "Post not found.");
    }

    @Test
    void testShowPostFormUpdate_postFound() {
        Member member = new Member();
        member.setId(1L);
        Post post = new Post();
        post.setId(1L);
        post.setMember(member);

        when(memberService.getAuthenticatedMember()).thenReturn(member);
        when(postService.findById(anyLong())).thenReturn(post);

        String view = postControllerMVC.showPostFormUpdate(1L, model);

        assertEquals("post-form", view);
        verify(model).addAttribute("post", post);
    }

    @Test
    void testSavePost_memberNotFound() {
        when(memberService.getAuthenticatedMember()).thenReturn(null);

        String view = postControllerMVC.savePost(new Post(), redirectAttributes);

        assertEquals("member-error", view);
    }

    @Test
    void testSavePost_memberFound() {
        Member member = new Member();
        member.setId(1L);
        Post post = new Post();
        post.setId(1L);
        post.setContent("This is a test post.");
        post.setMediaUrl("");

        when(memberService.getAuthenticatedMember()).thenReturn(member);
        when(badWordsFilterService.filterObsceneLanguage(anyString())).thenReturn(post.getContent());

        String view = postControllerMVC.savePost(post, redirectAttributes);

        assertEquals("redirect:/mvc/posts/?postId=1", view);
        verify(postService).save(post);
        verify(redirectAttributes).addFlashAttribute("success", "Post has been created.");
    }

    @Test
    void testUpdatePost_memberNotFound() {
        when(memberService.getAuthenticatedMember()).thenReturn(null);

        String view = postControllerMVC.updatePost(new Post(), redirectAttributes);

        assertEquals("member-error", view);
    }

    @Test
    void testUpdatePost_postFound() {
        Member member = new Member();
        member.setId(1L);
        Post post = new Post();
        post.setId(1L);
        post.setContent("Updated content.");
        Post existingPost = new Post();
        existingPost.setId(1L);

        when(memberService.getAuthenticatedMember()).thenReturn(member);
        when(postService.findById(anyLong())).thenReturn(existingPost);
        when(badWordsFilterService.filterObsceneLanguage(anyString())).thenReturn(post.getContent());

        String view = postControllerMVC.updatePost(post, redirectAttributes);

        assertEquals("redirect:/mvc/posts/?postId=1", view);
        verify(postService).save(existingPost);
        verify(redirectAttributes).addFlashAttribute("success", "Post has been updated.");
    }

    @Test
    void testDeletePost() {
        String view = postControllerMVC.deletePost(1L, redirectAttributes);

        assertEquals("redirect:/mvc/posts-your", view);
        verify(postService).deleteById(1L);
        verify(redirectAttributes).addFlashAttribute("success", "Post has been deleted.");
    }
}
