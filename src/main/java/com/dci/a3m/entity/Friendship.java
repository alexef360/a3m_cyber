package com.dci.a3m.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "friendships")
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "requester_id")
    private Member requester;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    private boolean accepted;

    // Default constructor
    public Friendship() {}

    // Constructor with parameters
    public Friendship(Member requester, Member receiver) {
        this.requester = requester;
        this.receiver = receiver;
        this.accepted = false; // Default value
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public Member getRequester() {
        return requester;
    }

    public void setRequester(Member requester) {
        this.requester = requester;
    }

    public Member getReceiver() {
        return receiver;
    }

    public void setReceiver(Member receiver) {
        this.receiver = receiver;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
