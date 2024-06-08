package com.dci.a3m.repository;

import com.dci.a3m.entity.Friendship;
import com.dci.a3m.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    List<Friendship> findByReceiverAndAccepted(Member receiver, boolean accepted);
    List<Friendship> findByRequesterAndAccepted(Member requester, boolean accepted);
    List<Friendship> findByRequesterAndReceiverAndAccepted(Member requester, Member receiver, boolean accepted);

    List<Friendship> findByRequesterOrReceiverAndAccepted(Member member, Member member1, boolean b);

    Optional<Object> findByRequesterAndReceiver(Member requester, Member receiver);
}
