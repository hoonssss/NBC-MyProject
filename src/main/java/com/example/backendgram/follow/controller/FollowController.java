package com.example.backendgram.follow.controller;

import com.example.backendgram.CommonResponseDto;
import com.example.backendgram.follow.dto.FollowResponseDto;
import com.example.backendgram.follow.service.FollowService;
import com.example.backendgram.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/profile/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("follow/{followerId}/{followingId}")
    public ResponseEntity<CommonResponseDto> followUser(@PathVariable User followerId, @PathVariable User followingId) {
        followService.followUser(followerId,followingId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("unfollow/{followerId}/{followingId}")
    public ResponseEntity<CommonResponseDto> unfollowUser(@PathVariable User followerId, @PathVariable User followingId) {
        followService.unFollowUser(followerId, followingId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<FollowResponseDto>> getfollower(){
        List<FollowResponseDto> followResponseDtos = followService.getFollower();
        return ResponseEntity.ok().body(followResponseDtos);
    }
}
