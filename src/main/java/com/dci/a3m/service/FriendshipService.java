package com.dci.a3m.service;

import com.dci.a3m.entity.Friendship;
import com.dci.a3m.entity.Member;

import java.util.List;

public interface FriendshipService {


    List<Friendship> getPendingRequests(Member member);

    List<Friendship> getSentRequests(Member member);

    List<Friendship> getFriends(Member member);

    void sendFriendRequest(Member requester, Member receiver);

    void acceptFriendRequest(Long id);

    void save(Friendship friendship);
}
