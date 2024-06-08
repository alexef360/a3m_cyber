package com.dci.a3m.service;

import com.dci.a3m.entity.Friendship;
import com.dci.a3m.entity.Member;
import com.dci.a3m.repository.FriendshipRepository;
import com.dci.a3m.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FriendshipServiceImpl implements FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public FriendshipServiceImpl(FriendshipRepository friendshipRepository, MemberRepository memberRepository) {
        this.friendshipRepository = friendshipRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public void save(Friendship friendship) {
        friendshipRepository.save(friendship);
    }

    @Override
    public List<Friendship> getPendingRequests(Member member) {
        return friendshipRepository.findByReceiverAndAccepted(member, false);
    }

    @Override
    public List<Friendship> getSentRequests(Member member) {
        return friendshipRepository.findByRequesterAndAccepted(member, false);
    }

    @Override
    public List<Friendship> getFriends(Member member) {
        return friendshipRepository.findByRequesterOrReceiverAndAccepted(member, member, true);
    }

    @Override
    @Transactional
    public void sendFriendRequest(Member requester, Member receiver) {
        if (friendshipRepository.findByRequesterAndReceiver(requester, receiver).isPresent()) {
            throw new RuntimeException("Friendship request already sent");
        }
        Friendship friendship = new Friendship(requester, receiver);
        friendshipRepository.save(friendship);
    }

    @Override
    @Transactional
    public void acceptFriendRequest(Long id) {
        Friendship friendship = friendshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Friendship with id " + id + " not found."));
        friendship.setAccepted(true);
        friendshipRepository.save(friendship);
    }
}
