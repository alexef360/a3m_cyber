package com.dci.a3m.controller;

import com.dci.a3m.entity.Friendship;
import com.dci.a3m.entity.Member;
import com.dci.a3m.service.FriendshipService;
import com.dci.a3m.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/mvc")
public class FriendshipControllerMVC {

    private final FriendshipService friendshipService;
    private final MemberService memberService;

    @Autowired
    public FriendshipControllerMVC(FriendshipService friendshipService, MemberService memberService) {
        this.friendshipService = friendshipService;
        this.memberService = memberService;
    }


    @PostMapping("/friendship-request")
    public String sendFriendRequest(@RequestParam("receiverId") Long receiverId) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member requester = memberService.findByUsername(userDetails.getUsername());
        Member receiver = memberService.findById(receiverId);
        friendshipService.sendFriendRequest(requester, receiver);
        return "redirect:/mvc/members";
    }

    @GetMapping("/friendship-requests")
    public String showFriendRequests(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member receiver = memberService.findByUsername(userDetails.getUsername());
        List<Friendship> requests = friendshipService.getPendingRequests(receiver);
        model.addAttribute("requests", requests);
        return "friendship-requests";
    }

    @PostMapping("/accept-friendship-request")
    public String acceptFriendRequest(@RequestParam("friendshipId") Long friendshipId) {
        friendshipService.acceptFriendRequest(friendshipId);
        return "redirect:/mvc/friendship-requests";
    }

    @GetMapping("/friends")
    public String showFriends(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Member member = memberService.findByUsername(userDetails.getUsername());
        List<Friendship> friends = friendshipService.getFriends(member);

        // Prepare attributes for Thymeleaf
        List<Map<String, Object>> friendDetails = friends.stream().map(friend -> {
            Map<String, Object> details = new HashMap<>();
            if (friend.getRequester().getId().equals(member.getId())) {
                details.put("profilePicture", friend.getReceiver().getProfilePicture());
                details.put("firstName", friend.getReceiver().getFirstName());
                details.put("lastName", friend.getReceiver().getLastName());
                details.put("username", friend.getReceiver().getUser().getUsername());
                details.put("memberId", friend.getReceiver().getId());
            } else {
                details.put("profilePicture", friend.getRequester().getProfilePicture());
                details.put("firstName", friend.getRequester().getFirstName());
                details.put("lastName", friend.getRequester().getLastName());
                details.put("username", friend.getRequester().getUser().getUsername());
                details.put("memberId", friend.getRequester().getId());
            }
            return details;
        }).collect(Collectors.toList());

        model.addAttribute("friendDetails", friendDetails);
        return "friends";
    }

}
