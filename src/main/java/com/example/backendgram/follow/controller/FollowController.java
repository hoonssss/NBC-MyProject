package com.example.backendgram.follow.controller;

import com.example.backendgram.CommonResponseDto;
import com.example.backendgram.follow.service.FollowService;
import com.example.backendgram.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/profile/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follow")
    public ResponseEntity<CommonResponseDto> followUser(@RequestParam Long followerId, @RequestParam Long followingId) {
        User follower = new User();
        follower.setId(followerId);

        User following = new User();
        following.setId(followingId);

        followService.followUser(follower, following);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/unfollow")
    public ResponseEntity<CommonResponseDto> unfollowUser(@RequestParam Long followerId, @RequestParam Long followingId) {
        User follower = new User();
        follower.setId(followerId);

        User following = new User();
        following.setId(followingId);

        followService.unfollowUser(follower, following);
        return ResponseEntity.ok().build();
    }
}
