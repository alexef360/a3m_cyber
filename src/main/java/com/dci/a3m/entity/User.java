package com.dci.a3m.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    private boolean enabled;

    private final LocalDateTime createdAt = LocalDateTime.now();

    // Mapping to the Authority entity
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "authority_id")
    private Authority authority;

    // Mapping to the Admin entity
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    // Mapping to the Member entity
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;


    // Constructors

    public User() {
    }

    public User(String username, String email, String password, boolean enabled, Authority authority) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.authority = authority;
    }

    public User(String username, String email, String password, boolean enabled, Authority authority, Admin admin) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.authority = authority;
        this.admin = admin;
    }

    public User(String username, String email, String password, boolean enabled, Authority authority, Member member) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.authority = authority;
        this.member = member;
    }

    // Getters and Setters


    public Authority getAuthority() {
        return authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


}


