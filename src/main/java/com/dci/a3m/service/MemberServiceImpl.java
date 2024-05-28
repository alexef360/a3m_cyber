package com.dci.a3m.service;

import com.dci.a3m.entity.Authority;
import com.dci.a3m.entity.Member;
import com.dci.a3m.entity.User;
import com.dci.a3m.repository.MemberRepository;
import com.dci.a3m.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Initial Records for Admin in the Database
    @PostConstruct
    public void initMember() {

        // password = username
        String username = "MemberExample";
        String password = passwordEncoder.encode(username);
        String email = "member@example.com";
        createMember(
                username,
                email,
                password,
                "Alice",
                "River",
                LocalDate.of(1990, 1, 1),
                "https://images.unsplash.com/photo-1479936343636-73cdc5aae0c3?q=80&w=1160&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                LocalDate.now(),
                "New York",
                "USA",
                "10101",
                "1234567890");
    }


    // CRUD OPERATIONS

    // READ ALL
    @Override
    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    // READ BY ID
    @Override
    public Member findById(Long id) {
        Optional<Member> result = memberRepository.findById(id);

        Member member = null;

        if (result.isPresent()) {
            member = result.get();
        } else {
            throw new RuntimeException("Member with id " + id + " not found.");
        }

        return member;
    }

    // SAVE
    @Override
    public void save(Member member) {
        memberRepository.save(member);
    }

    // CREATE
    @Override
    public void createMember(
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
            String phone) {

        Member member = new Member(firstName, lastName, birthDate, profilePicture, createdAt, city, country, postalCode, phone);

        Authority authority = new Authority(username, member.getRole());

        User user = new User(username, email, password, true, authority, member);

        userRepository.save(user);
    }

    // UPDATE
    @Override
    public void update(Member member) {
        memberRepository.save(member);
    }

    // DELETE
    @Override
    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }
}
