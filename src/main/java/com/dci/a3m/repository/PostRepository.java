package com.dci.a3m.repository;


import com.dci.a3m.entity.Member;
import com.dci.a3m.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
     List<Post> findAllByMember(Member member);
}
