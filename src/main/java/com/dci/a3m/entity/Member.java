package com.dci.a3m.entity;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String Role = "ROLE_MEMBER";

    // Mapping with User
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private User user;

    // PERSONAL INFO
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String profilePicture;


    // ADDRESS
    private String city;
    private String country;
    private String postalCode;
    private String phone;


    // CONSTRUCTORS
    public Member() {
    }

    public Member(String firstName, String lastName, LocalDate birthDate, String profilePicture, String city, String country, String postalCode, String phone) {


        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.profilePicture = profilePicture;


        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
        this.phone = phone;
    }

    // GETTERS AND SETTERS


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }



    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
