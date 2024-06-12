package com.dci.a3m.service;

import com.dci.a3m.entity.Like;
import com.dci.a3m.entity.Member;

import java.time.LocalDate;
import java.util.List;

public interface MemberService {

    // CRUD OPERATIONS
    // READ ALL
    List<Member> findAll();

    // READ BY ID
    Member findById(Long id);

    // READ BY USERNAME
    Member findByUsername(String username);

    // SAVE
    void save(Member member);

    // UPDATE
    void update(Member member);

    // DELETE BY ID
    void deleteById(Long id);

    // AUTHENTICATION
    public Member getAuthenticatedMember();

    // DELETE ALL
    void deleteAll();

    void createInitMembers();
}

