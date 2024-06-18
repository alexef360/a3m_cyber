package com.dci.a3m.controller;

import com.dci.a3m.entity.Comment;
import com.dci.a3m.entity.Like;
import com.dci.a3m.entity.Member;
import com.dci.a3m.entity.Post;
import com.dci.a3m.service.CommentService;
import com.dci.a3m.service.LikeService;
import com.dci.a3m.service.MemberService;
import com.dci.a3m.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class LikeControllerTest {

    @Mock
    private MemberService memberService;

    @Mock
    private PostService postService;

    @Mock
    private LikeService likeService;

    @Mock
    private CommentService commentService;

    @InjectMocks
    private LikeController likeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLikePostInfo_authenticatedMemberNotFound() {
        when(memberService.getAuthenticatedMember()).thenReturn(null);

        String view = likeController.likePostInfo(1L);

        assertEquals("member-error", view);
    }

    @Test
    void testLikePostInfo_postNotFound() {
        Member member = new Member();
        when(memberService.getAuthenticatedMember()).thenReturn(member);
        when(postService.findById(anyLong())).thenReturn(null);

        String view = likeController.likePostInfo(1L);

        assertEquals("post-error", view);
    }

    @Test
    void testLikePostInfo_alreadyLiked() {
        Member member = new Member();
        Post post = new Post();
        when(memberService.getAuthenticatedMember()).thenReturn(member);
        when(postService.findById(anyLong())).thenReturn(post);
        when(likeService.findByMemberAndPost(member, post)).thenReturn(new Like());

        String view = likeController.likePostInfo(1L);

        assertEquals("redirect:/mvc/posts/?postId=1", view);
    }

    @Test
    void testLikePostInfo_success() {
        Member member = new Member();
        Post post = new Post();
        post.setLikes(new ArrayList<>());
        when(memberService.getAuthenticatedMember()).thenReturn(member);
        when(postService.findById(anyLong())).thenReturn(post);
        when(likeService.findByMemberAndPost(member, post)).thenReturn(null);

        String view = likeController.likePostInfo(1L);

        assertEquals("redirect:/mvc/posts/?postId=1", view);
        verify(likeService).save(any(Like.class));
    }

    @Test
    void testUnlikePostInfo_authenticatedMemberNotFound() {
        when(memberService.getAuthenticatedMember()).thenReturn(null);

        String view = likeController.unlikePostInfo(1L);

        assertEquals("member-error", view);
    }

    @Test
    void testUnlikePostInfo_postNotFound() {
        Member member = new Member();
        when(memberService.getAuthenticatedMember()).thenReturn(member);
        when(postService.findById(anyLong())).thenReturn(null);

        String view = likeController.unlikePostInfo(1L);

        assertEquals("post-error", view);
    }

    @Test
    void testUnlikePostInfo_likeNotFound() {
        Member member = new Member();
        Post post = new Post();
        post.setLikes(new ArrayList<>());
        when(memberService.getAuthenticatedMember()).thenReturn(member);
        when(postService.findById(anyLong())).thenReturn(post);
        when(likeService.findByMemberAndPost(member, post)).thenReturn(null);

        String view = likeController.unlikePostInfo(1L);

        assertEquals("redirect:/mvc/posts/?postId=1", view);
    }

    @Test
    void testUnlikePostInfo_success() {
        Member member = new Member();
        Post post = new Post();
        Like like = new Like();
        post.setLikes(new ArrayList<>());
        post.getLikes().add(like);
        when(memberService.getAuthenticatedMember()).thenReturn(member);
        when(postService.findById(anyLong())).thenReturn(post);
        when(likeService.findByMemberAndPost(member, post)).thenReturn(like);

        String view = likeController.unlikePostInfo(1L);

        assertEquals("redirect:/mvc/posts/?postId=1", view);
        verify(likeService).deleteById(like.getId());
    }

    @Test
    void testLikeComment_authenticatedMemberNotFound() {
        when(memberService.getAuthenticatedMember()).thenReturn(null);

        String view = likeController.likeComment(1L);

        assertEquals("member-error", view);
    }

    @Test
    void testLikeComment_commentNotFound() {
        Member member = new Member();
        when(memberService.getAuthenticatedMember()).thenReturn(member);
        when(commentService.findById(anyLong())).thenReturn(null);

        String view = likeController.likeComment(1L);

        assertEquals("comment-error", view);
    }

    @Test
    void testLikeComment_alreadyLiked() {
        Member member = new Member();
        Comment comment = new Comment();
        Post post = new Post();
        comment.setPost(post);
        when(memberService.getAuthenticatedMember()).thenReturn(member);
        when(commentService.findById(anyLong())).thenReturn(comment);
        when(likeService.findByMemberAndComment(member, comment)).thenReturn(new Like());

        String view = likeController.likeComment(1L);

        assertEquals("redirect:/mvc/posts/?postId=" + comment.getPost().getId(), view);
    }

    @Test
    void testLikeComment_success() {
        Member member = new Member();
        Comment comment = new Comment();
        Post post = new Post();
        comment.setPost(post);
        comment.setLikes(new ArrayList<>());
        when(memberService.getAuthenticatedMember()).thenReturn(member);
        when(commentService.findById(anyLong())).thenReturn(comment);
        when(likeService.findByMemberAndComment(member, comment)).thenReturn(null);

        String view = likeController.likeComment(1L);

        assertEquals("redirect:/mvc/posts/?postId=" + comment.getPost().getId(), view);
        verify(likeService).save(any(Like.class));
    }

    @Test
    void testUnlikeComment_authenticatedMemberNotFound() {
        when(memberService.getAuthenticatedMember()).thenReturn(null);

        String view = likeController.unlikeComment(1L);

        assertEquals("member-error", view);
    }

    @Test
    void testUnlikeComment_commentNotFound() {
        Member member = new Member();
        when(memberService.getAuthenticatedMember()).thenReturn(member);
        when(commentService.findById(anyLong())).thenReturn(null);

        String view = likeController.unlikeComment(1L);

        assertEquals("comment-error", view);
    }

    @Test
    void testUnlikeComment_likeNotFound() {
        Member member = new Member();
        Comment comment = new Comment();
        Post post = new Post();
        comment.setPost(post);
        comment.setLikes(new ArrayList<>());
        when(memberService.getAuthenticatedMember()).thenReturn(member);
        when(commentService.findById(anyLong())).thenReturn(comment);
        when(likeService.findByMemberAndComment(member, comment)).thenReturn(null);

        String view = likeController.unlikeComment(1L);

        assertEquals("redirect:/mvc/posts/?postId=" + comment.getPost().getId(), view);
    }

    @Test
    void testUnlikeComment_success() {
        Member member = new Member();
        Comment comment = new Comment();
        Post post = new Post();
        comment.setPost(post);
        Like like = new Like();
        comment.setLikes(new ArrayList<>());
        comment.getLikes().add(like);
        when(memberService.getAuthenticatedMember()).thenReturn(member);
        when(commentService.findById(anyLong())).thenReturn(comment);
        when(likeService.findByMemberAndComment(member, comment)).thenReturn(like);

        String view = likeController.unlikeComment(1L);

        assertEquals("redirect:/mvc/posts/?postId=" + comment.getPost().getId(), view);
        verify(likeService).deleteById(like.getId());
    }
}
