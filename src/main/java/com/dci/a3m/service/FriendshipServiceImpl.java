package com.dci.a3m.service;

import com.dci.a3m.entity.FriendshipInvitation;
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


    // CRUD OPERATIONS

    // READ

    // CREATE

    // UPDATE

    // DELETE


    @Override
    public void save(FriendshipInvitation friendshipInvitation) {
        friendshipRepository.save(friendshipInvitation);
    }

    @Override
    public List<FriendshipInvitation> findByAcceptingMemberAndNotAccepted(Member member) {
        return friendshipRepository.findByAcceptingMemberAndAccepted(member, false);
    }

    @Override
    public List<FriendshipInvitation> findByInvitingMemberAndNotAccepted(Member member) {
        return friendshipRepository.findByInvitingMemberAndAccepted(member, false);
    }

    @Override
    public List<FriendshipInvitation> findFriendsAccepted(Member member) {
        return friendshipRepository.findByInvitingMemberOrAcceptingMemberAndAccepted(member, member, true);
    }

    @Override
    @Transactional
    public void createFriendshipInvitation(Member invitingMember, Member acceptingMember) {
        if (friendshipRepository.findByInvitingMemberAndAcceptingMember(invitingMember, acceptingMember).isPresent()) {
            throw new RuntimeException("Friendship request already sent");
        }
        FriendshipInvitation friendshipInvitation = new FriendshipInvitation(invitingMember, acceptingMember);
        friendshipRepository.save(friendshipInvitation);
    }

    @Override
    @Transactional
    public void acceptFriendshipInvitation(Long id) {
        FriendshipInvitation friendshipInvitation = friendshipRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Friendship with id " + id + " not found."));
        friendshipInvitation.setAccepted(true);
        friendshipRepository.save(friendshipInvitation);
    }

    // DELETE
    @Override
    public void declineFriendshipInvitation(Long id) {
        friendshipRepository.deleteById(id);
    }
}
