package com.dci.a3m.repository;


import com.dci.a3m.entity.Member;
import com.dci.a3m.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

     List<Post> findAllByMember(Member member);

     @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
     List<Post> findAllOrderByCreatedAtDesc();

     List<Post> findByMemberIdIn(List<Long> memberIds);
}
