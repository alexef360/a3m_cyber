package com.dci.a3m.service;

import com.dci.a3m.entity.Comment;
import com.dci.a3m.entity.Post;
import com.dci.a3m.entity.Member;
import com.dci.a3m.repository.CommentRepository;
import com.dci.a3m.repository.PostRepository;
import com.dci.a3m.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;

    public PostServiceImpl(PostRepository postRepository, MemberRepository memberRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
        this.commentRepository = commentRepository;
    }

    // CRUD OPERATIONS

    // READ ALL

    @Override
    public List<Post> findAll() {
//        return postRepository.findAllOrderByCreatedAtDesc(); // Descending order
        return postRepository.findAll();
    }

    // READ ALL BY MEMBER
    @Override
    public List<Post> findAllByMember(Member member) {

        // find all posts by member
        List<Post> posts = postRepository.findAllByMember(member);
        return posts;
    }


    // READ BY ID
    @Override
    public Post findById(Long id) {
        Optional<Post> result = postRepository.findById(id);

        Post post = null;

        if (result.isPresent()) {
            post = result.get();
        } else {
            throw new RuntimeException("Did not find post id - " + id);
        }
        return post;
    }

    // SAVE
    @Override
    public void save(Post post) {
        postRepository.save(post);

    }

    // UPDATE
    @Override
    public void update(Post post) {
        if (post.getId() != null) {
            postRepository.save(post);
        } else {
            throw new RuntimeException("Post id is null");
        }
    }

    // DELETE
    @Override
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }


    // Initial Records for Post in the Database
//    @PostConstruct
//    public void initPost() {
//
//
//        // Fetch a member to associate with the posts
//        Member member1 = memberRepository.findById(1L).orElse(null);
//        if (member1 == null) {
//            throw new RuntimeException("Member not found");
//        }
//
//        String mediaUrl1 = "https://images.unsplash.com/photo-1499063078284-f78f7d89616a?q=80&w=928&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";
//
//        String mediaUrl2 = "https://images.unsplash.com/photo-1448518340475-e3c680e9b4be?q=80&w=1171&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";
//
//        String mediaUrl3 = "https://images.unsplash.com/photo-1554058501-f6872d688003?q=80&w=1169&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";
//
//        String mediaUrl4 = "https://images.unsplash.com/photo-1600628421060-939639517883?q=80&w=1170&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";
//
//        String mediaUrl5 = "https://images.unsplash.com/photo-1527455102718-437c64ea37ad?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";
//
//        String mediaUrl6 = "https://images.unsplash.com/photo-1574968583205-1403e3940bc4?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D";
//
//
//        // Create some initial posts
//        Post post1 = new Post("First post content", mediaUrl1, member1);
//        Post post2 = new Post("Second post content", mediaUrl2, member1);
//        Post post3 = new Post("Third post content", mediaUrl3, member1);
//
//        // Save the initial posts to the database
//        postRepository.save(post1);
//        postRepository.save(post2);
//        postRepository.save(post3);
//
//        // Create some initial comments for the first post
//        Comment comment1 = new Comment("First comment content", member1, post1);
//        Comment comment2 = new Comment("Second comment content", member1, post1);
//        Comment comment3 = new Comment("Third comment content", member1, post1);
//
//        // Save the initial comments to the database
//        commentRepository.save(comment1);
//        commentRepository.save(comment2);
//        commentRepository.save(comment3);
//
//
//        // Fetch a member to associate with the posts
//        Member member2 = memberRepository.findById(2L).orElse(null);
//        if (member2 == null) {
//            throw new RuntimeException("Member not found");
//        }
//
//        // Create some initial posts
//        Post post4 = new Post("First post content",mediaUrl4,  member2);
//        Post post5 = new Post("Second post content",mediaUrl5,  member2);
//
//
//        // Save the initial posts to the database
//        postRepository.save(post4);
//        postRepository.save(post5);
//
//        // Create some initial comments for the first post
//        Comment comment4 = new Comment("First comment content", member2, post4);
//        Comment comment5 = new Comment("Second comment content", member2, post4);
//        // Save the initial comments to the database
//        commentRepository.save(comment4);
//        commentRepository.save(comment5);
//
//        // Fetch a member to associate with the posts
//        Member member3 = memberRepository.findById(3L).orElse(null);
//        if (member3 == null) {
//            throw new RuntimeException("Member not found");
//        }
//
//        // Create some initial posts
//        Post post6 = new Post("First post content",mediaUrl6,  member3);
//
//        // Save the initial posts to the database
//        postRepository.save(post6);
//
//        // Create some initial comments for the first post
//        Comment comment6 = new Comment("First comment content", member3, post6);
//
//        // Save the initial comments to the database
//        commentRepository.save(comment6);
//
//    }

}
