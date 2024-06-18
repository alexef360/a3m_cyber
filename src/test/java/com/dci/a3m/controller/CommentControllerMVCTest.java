package com.dci.a3m.controller;

import com.dci.a3m.entity.Comment;
import com.dci.a3m.entity.Member;
import com.dci.a3m.entity.Post;
import com.dci.a3m.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CommentControllerMVCTest {

    @Mock
    private UserDetailsManager userDetailsManager;

    @Mock
    private CommentService commentService;

    @Mock
    private MemberService memberService;

    @Mock
    private PostService postService;

    @Mock
    private UserService userService;

    @Mock
    private BadWordsFilterService badWordsFilterService;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private CommentControllerMVC commentControllerMVC;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        List<Comment> comments = List.of(new Comment());
        when(commentService.findAll()).thenReturn(comments);

        String view = commentControllerMVC.findAll(model);

        assertEquals("comments", view);
        verify(model).addAttribute("comments", comments);
    }

    @Test
    void testFindAllByMember_memberFound() {
        Member member = new Member();
        List<Comment> comments = List.of(new Comment());
        when(memberService.getAuthenticatedMember()).thenReturn(member);
        when(commentService.findAllByMember(member)).thenReturn(comments);

        String view = commentControllerMVC.findAllByMember(model);

        assertEquals("comments", view);
        verify(model).addAttribute("comments", comments);
    }

    @Test
    void testFindAllByMember_memberNotFound() {
        when(memberService.getAuthenticatedMember()).thenReturn(null);

        String view = commentControllerMVC.findAllByMember(model);

        assertEquals("member-error", view);
        verify(model).addAttribute("error", "Member not found.");
    }

    @Test
    void testGetCommentById_commentFound() {
        Comment comment = new Comment();
        when(commentService.findById(anyLong())).thenReturn(comment);

        String view = commentControllerMVC.getCommentById(1L, model);

        assertEquals("comment-info", view);
        verify(model).addAttribute("comment", comment);
    }

    @Test
    void testGetCommentById_commentNotFound() {
        when(commentService.findById(anyLong())).thenReturn(null);

        String view = commentControllerMVC.getCommentById(1L, model);

        assertEquals("comment-error", view);
        verify(model).addAttribute("error", "Comment not found.");
    }

    @Test
    void testShowCommentForm_memberFound() {
        Member member = new Member();
        when(memberService.getAuthenticatedMember()).thenReturn(member);

        String view = commentControllerMVC.showCommentForm(1L, model);

        assertEquals("comment-form", view);
        verify(model).addAttribute("postId", 1L);
        verify(model).addAttribute(eq("comment"), any(Comment.class));
    }

    @Test
    void testShowCommentForm_memberNotFound() {
        when(memberService.getAuthenticatedMember()).thenReturn(null);

        String view = commentControllerMVC.showCommentForm(1L, model);

        assertEquals("member-error", view);
        verify(model).addAttribute("error", "Member not found.");
    }

    @Test
    void testShowCommentFormUpdate_commentFound() {
        Comment comment = new Comment();
        Post post = new Post();
        post.setId(1L);
        comment.setPost(post);
        when(commentService.findById(anyLong())).thenReturn(comment);

        String view = commentControllerMVC.showCommentFormUpdate(1L, model);

        assertEquals("comment-form", view);
        verify(model).addAttribute("postId", 1L);
        verify(model).addAttribute("comment", comment);
    }

    @Test
    void testShowCommentFormUpdate_commentNotFound() {
        when(commentService.findById(anyLong())).thenReturn(null);

        String view = commentControllerMVC.showCommentFormUpdate(1L, model);

        assertEquals("comment-error", view);
        verify(model).addAttribute("error", "Comment not found.");
    }

    @Test
    void testSaveComment_memberFound() {
        Member member = new Member();
        Post post = new Post();
        Comment comment = new Comment();
        comment.setContent("Test content");
        when(memberService.getAuthenticatedMember()).thenReturn(member);
        when(postService.findById(anyLong())).thenReturn(post);
        when(badWordsFilterService.filterObsceneLanguage(anyString())).thenReturn("Filtered content");

        String view = commentControllerMVC.saveComment(1L, comment, redirectAttributes);

        assertEquals("redirect:/mvc/posts/?postId=1", view);
        assertEquals("Filtered content", comment.getContent());
        verify(commentService).save(comment);
        verify(redirectAttributes).addFlashAttribute("success", "Comment has been created.");
    }

    @Test
    void testSaveComment_memberNotFound() {
        when(memberService.getAuthenticatedMember()).thenReturn(null);

        Comment comment = new Comment();
        String view = commentControllerMVC.saveComment(1L, comment, redirectAttributes);

        assertEquals("comment-error", view);
        verifyNoInteractions(postService, badWordsFilterService, commentService);
    }

    @Test
    void testUpdateComment_commentFound() {
        Comment existingComment = new Comment();
        existingComment.setId(1L);
        Post post = new Post();
        post.setId(1L);
        existingComment.setPost(post);
        Comment newComment = new Comment();
        newComment.setId(1L);
        newComment.setContent("New content");
        when(commentService.findById(anyLong())).thenReturn(existingComment);
        when(badWordsFilterService.filterObsceneLanguage(anyString())).thenReturn("Filtered content");

        String view = commentControllerMVC.updateComment(newComment, redirectAttributes);

        assertEquals("redirect:/mvc/posts/?postId=1", view);
        assertEquals("Filtered content", existingComment.getContent());
        verify(commentService).save(existingComment);
        verify(redirectAttributes).addFlashAttribute("success", "Comment has been updated.");
    }

    @Test
    void testUpdateComment_commentNotFound() {
        when(commentService.findById(anyLong())).thenReturn(null);

        Comment comment = new Comment();
        String view = commentControllerMVC.updateComment(comment, redirectAttributes);

        assertEquals("comment-error", view);
        verify(commentService, never()).save(any());
        verifyNoInteractions(badWordsFilterService);
    }

    @Test
    void testDeleteComment_commentFound() {
        Comment comment = new Comment();
        Post post = new Post();
        post.setId(1L);
        comment.setPost(post);
        when(commentService.findById(anyLong())).thenReturn(comment);

        String view = commentControllerMVC.deleteComment(1L, redirectAttributes);

        assertEquals("redirect:/mvc/posts/?postId=1", view);
        verify(commentService).deleteById(1L);
        verify(redirectAttributes).addFlashAttribute("success", "Comment has been deleted.");
    }
}
