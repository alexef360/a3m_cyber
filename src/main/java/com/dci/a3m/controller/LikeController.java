package com.dci.a3m.controller;

import com.dci.a3m.entity.Comment;
import com.dci.a3m.entity.Like;
import com.dci.a3m.entity.Member;
import com.dci.a3m.entity.Post;
import com.dci.a3m.service.CommentService;
import com.dci.a3m.service.LikeService;
import com.dci.a3m.service.MemberService;
import com.dci.a3m.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mvc")
public class LikeController {

    private final MemberService memberService;
    private final PostService postService;
    private final LikeService likeService;
    private final CommentService commentService;

    @Autowired
    public LikeController(MemberService memberService, PostService postService, LikeService likeService, CommentService commentService) {
        this.memberService = memberService;
        this.postService = postService;
        this.likeService = likeService;
        this.commentService = commentService;
    }

    // POSTS

//    // LIKE POST
//    @PostMapping("/like-post")
//    public String likePost(@RequestParam("postId") Long id) {
//        // Currently logged in member
//        Member authenticatedMember = memberService.getAuthenticatedMember();
//        if (authenticatedMember == null) {
//            return "member-error";
//        }
//        // Find post
//        Post post = postService.findById(id);
//        if (post == null) {
//            return "post-error";
//        }
//
//        // Check if member has already liked the post
//        Like existingLike = likeService.findByMemberAndPost(authenticatedMember, post);
//        if (existingLike != null) {
//            return "redirect:/mvc/posts";
//        }
//
//        // Like the post
//        Like like = new Like(authenticatedMember, post);
//        post.getLikes().add(like);
//        likeService.save(like);
//        return "redirect:/mvc/posts";
//    }
//
//    // UNLIKE POST
//    @PostMapping("/unlike-post")
//    public String unlikePost(@RequestParam("postId") Long id) {
//        // Currently logged in member
//        Member authenticatedMember = memberService.getAuthenticatedMember();
//        if (authenticatedMember == null) {
//            return "member-error";
//        }
//
//        Post post = postService.findById(id);
//        if (post == null) {
//            return "post-error";
//        }
//
//        Like like = likeService.findByMemberAndPost(authenticatedMember, post);
//        if (like == null) {
//            return "redirect:/mvc/posts";
//        }
//
//        post.getLikes().remove(like);
//        likeService.deleteById(like.getId());
//        return "redirect:/mvc/posts";
//    }

    // LIKE POST FROM THE POST-INFO
    @PostMapping("/like-post-info")
    public String likePostInfo(@RequestParam("postId") Long id) {
        // Currently logged in member
        Member authenticatedMember = memberService.getAuthenticatedMember();
        if (authenticatedMember == null) {
            return "member-error";
        }
        // Find post
        Post post = postService.findById(id);
        if (post == null) {
            return "post-error";
        }

        // Check if member has already liked the post
        Like existingLike = likeService.findByMemberAndPost(authenticatedMember, post);
        if (existingLike != null) {
            return "redirect:/mvc/posts/?postId=" + id;
        }

        // Like the post
        Like like = new Like(authenticatedMember, post);
        post.getLikes().add(like);
        likeService.save(like);
        return "redirect:/mvc/posts/?postId=" + id;
    }

    // UNLIKE POST FROM THE POST-INFO
    @PostMapping("/unlike-post-info")
    public String unlikePostInfo(@RequestParam("postId") Long id) {
        // Currently logged in member
        Member authenticatedMember = memberService.getAuthenticatedMember();
        if (authenticatedMember == null) {
            return "member-error";
        }

        Post post = postService.findById(id);
        if (post == null) {
            return "post-error";
        }

        Like like = likeService.findByMemberAndPost(authenticatedMember, post);
        if (like == null) {
            return "redirect:/mvc/posts/?postId=" + id;
        }

        post.getLikes().remove(like);
        likeService.deleteById(like.getId());
        return "redirect:/mvc/posts/?postId=" + id;
    }

    // LIKE POST FROM OWN PROFILE
    @PostMapping("/like-post-profile")
    public String likePostProfile(@RequestParam("postId") Long id) {
        // Currently logged in member
        Member authenticatedMember = memberService.getAuthenticatedMember();
        if (authenticatedMember == null) {
            return "member-error";
        }
        // Find post
        Post post = postService.findById(id);
        if (post == null) {
            return "post-error";
        }

        // Check if member has already liked the post
        Like existingLike = likeService.findByMemberAndPost(authenticatedMember, post);
        if (existingLike != null) {
            return "redirect:/login-success";
        }

        // Like the post
        Like like = new Like(authenticatedMember, post);
        post.getLikes().add(like);
        likeService.save(like);
        return "redirect:/login-success";
    }

    // UNLIKE POST FROM OWN PROFILE
    @PostMapping("/unlike-post-profile")
    public String unlikePostProfile(@RequestParam("postId") Long id) {
        // Currently logged in member
        Member authenticatedMember = memberService.getAuthenticatedMember();
        if (authenticatedMember == null) {
            return "member-error";
        }

        Post post = postService.findById(id);
        if (post == null) {
            return "post-error";
        }

        Like like = likeService.findByMemberAndPost(authenticatedMember, post);
        if (like == null) {
            return "redirect:/login-success";
        }

        post.getLikes().remove(like);
        likeService.deleteById(like.getId());
        return "redirect:/login-success";
    }

    // LIKE POST FROM FRIENDS POSTS VIEW
    @PostMapping("/like-post-friend")
    public String likePostFriends(@RequestParam("postId") Long id) {
        // Currently logged in member
        Member authenticatedMember = memberService.getAuthenticatedMember();
        if (authenticatedMember == null) {
            return "member-error";
        }
        // Find post
        Post post = postService.findById(id);
        if (post == null) {
            return "post-error";
        }

        // Check if member has already liked the post
        Like existingLike = likeService.findByMemberAndPost(authenticatedMember, post);
        if (existingLike != null) {
            return "redirect:/mvc/posts-of-friends";
        }

        // Like the post
        Like like = new Like(authenticatedMember, post);
        post.getLikes().add(like);
        likeService.save(like);
        return "redirect:/mvc/posts-of-friends";
    }

    // UNLIKE POST FROM FRIENDS POSTS VIEW
    @PostMapping("/unlike-post-friend")
    public String unlikePostFriends(@RequestParam("postId") Long id) {
        // Currently logged in member
        Member authenticatedMember = memberService.getAuthenticatedMember();
        if (authenticatedMember == null) {
            return "member-error";
        }

        Post post = postService.findById(id);
        if (post == null) {
            return "post-error";
        }

        Like like = likeService.findByMemberAndPost(authenticatedMember, post);
        if (like == null) {
            return "redirect:/mvc/posts-of-friends";
        }

        post.getLikes().remove(like);
        likeService.deleteById(like.getId());
        return "redirect:/mvc/posts-of-friends";
    }

    // LIKE POST FROM YOUR POSTS VIEW
    @PostMapping("/like-post-your")
    public String likePostYour(@RequestParam("postId") Long id) {
        // Currently logged in member
        Member authenticatedMember = memberService.getAuthenticatedMember();
        if (authenticatedMember == null) {
            return "member-error";
        }
        // Find post
        Post post = postService.findById(id);
        if (post == null) {
            return "post-error";
        }

        // Check if member has already liked the post
        Like existingLike = likeService.findByMemberAndPost(authenticatedMember, post);
        if (existingLike != null) {
            return "redirect:/mvc/posts-your";
        }

        // Like the post
        Like like = new Like(authenticatedMember, post);
        post.getLikes().add(like);
        likeService.save(like);
        return "redirect:/mvc/posts-your";
    }

    // UNLIKE POST FROM YOUR POSTS VIEW
    @PostMapping("/unlike-post-your")
    public String unlikePostYour(@RequestParam("postId") Long id) {
        // Currently logged in member
        Member authenticatedMember = memberService.getAuthenticatedMember();
        if (authenticatedMember == null) {
            return "member-error";
        }

        Post post = postService.findById(id);
        if (post == null) {
            return "post-error";
        }

        Like like = likeService.findByMemberAndPost(authenticatedMember, post);
        if (like == null) {
            return "redirect:/mvc/posts-your";
        }

        post.getLikes().remove(like);
        likeService.deleteById(like.getId());
        return "redirect:/mvc/posts-your";
    }

    // COMMENTS

    // LIKE COMMENT
    @PostMapping("/like-comment")
    public String likeComment(@RequestParam("commentId") Long id) {
        // Currently logged in member
        Member member = memberService.getAuthenticatedMember();
        if (member == null) {
            return "member-error";
        }

        //Find comment
        Comment comment = commentService.findById(id);
        if (comment == null) {
            return "comment-error";
        }

        // Check if member has liked the comment
        Like existinLike = likeService.findByMemberAndComment(member, comment);
        if (existinLike != null) {
            return "redirect:/mvc/posts/?postId=" + comment.getPost().getId();
        }

        // Like the comment
        Like like = new Like(member, comment);
        comment.getLikes().add(like);
        likeService.save(like);
        return "redirect:/mvc/posts/?postId=" + comment.getPost().getId();
    }

    // UNLIKE COMMENT
    @PostMapping("/unlike-comment")
    public String unlikeComment(@RequestParam("commentId") Long id) {
        // Currently logged in member
        Member member = memberService.getAuthenticatedMember();
        if (member == null) {
            return "member-error";
        }
        //Find the comment
        Comment comment = commentService.findById(id);
        if (comment == null) {
            return "comment-error";
        }

        Like like = likeService.findByMemberAndComment(member, comment);
        if (like == null) {
            return "redirect:/mvc/posts/?postId=" + comment.getPost().getId();
        }

        comment.getLikes().remove(like);
        likeService.deleteById(like.getId());
        return "redirect:/mvc/posts/?postId=" + comment.getPost().getId();
    }
}
