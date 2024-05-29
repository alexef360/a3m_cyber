package com.dci.a3m.service;

import com.dci.a3m.entity.Member;

import java.time.LocalDate;
import java.util.List;

public interface MemberService {

    // CRUD OPERATIONS
    // READ ALL
    List<Member> findAll();

    // READ BY ID
    Member findById(Long id);


    // SAVE
    void save(Member member);

    // CREATE
    void createMember(
            String username,
            String email,
            String password,
            String firstName,
            String lastName,
            LocalDate birthDate,
            String profilePicture,
            LocalDate createdAt,
            String city,
            String country,
            String postalCode,
            String phone
            );

    // UPDATE
    void update(Member member);

    // DELETE BY ID
    void deleteById(Long id);

}

