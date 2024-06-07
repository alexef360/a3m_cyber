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

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, UserRepository userRepository ) {
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
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


    // UPDATE
    @Override
    public void update(Member member) {
        if (member.getId() != null) {
            memberRepository.save(member);
        } else {
            throw new IllegalArgumentException("Member ID must not be null for update operation");
        }
    }

    // DELETE
    @Override
    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

}
