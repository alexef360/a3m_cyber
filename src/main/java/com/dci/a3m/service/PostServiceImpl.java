package com.dci.a3m.service;

import com.dci.a3m.entity.Post;
import com.dci.a3m.entity.Member;
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

    public PostServiceImpl(PostRepository postRepository, MemberRepository memberRepository) {
        this.postRepository = postRepository;
        this.memberRepository = memberRepository;
    }

    // Initial Records for Post in the Database
    @PostConstruct
    public void initPost() {
        // Check if there are already posts in the database
        if (postRepository.count() == 0) {
            // Fetch a member to associate with the posts
            Member member = memberRepository.findById(1L).orElse(null);
            if (member == null) {
                throw new RuntimeException("Member not found");
            }

            // Create some initial posts
            Post post1 = new Post("First post content", member);
            Post post2 = new Post("Second post content", member);
            Post post3 = new Post("Third post content", member);

            // Save the initial posts to the database
            postRepository.save(post1);
            postRepository.save(post2);
            postRepository.save(post3);
        }
    }

    // CRUD OPERATIONS

    // READ ALL

    @Override
    public List<Post> findAll() {
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


}
