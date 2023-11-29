package com.example.backendgram.follow.service;

import com.example.backendgram.follow.dto.FollowResponseDto;
import com.example.backendgram.follow.entity.Follow;
import com.example.backendgram.follow.repository.FollowRepository;
import com.example.backendgram.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;

    @Transactional
    public void followUser(User followerId, User followingId) {
        if(!isFollowing(followerId,followingId)){
            Follow follow = new Follow();
            follow.setFollower(followerId);
            follow.setFollowing(followingId);
            followRepository.save(follow);
        }
    }

    @Transactional
    public void unFollowUser(User followerId, User followingId) {
        if(isFollowing(followerId,followingId)){
            followRepository.deleteByFollowerIdAndFollowingId(followerId,followingId);
        }
    }

    public List<FollowResponseDto> getFollower(){
        return followRepository.findAll().stream().map(
                follow -> {
                    FollowResponseDto dto = new FollowResponseDto();
                    dto.setFollowing(follow.getFollowing());
                    dto.setFollower(follow.getFollower());
                    return dto;
                }
        ).collect(Collectors.toList());
    }

    public boolean isFollowing(User follower, User following) {
        return followRepository.existsByFollowerAndFollowing(follower, following);
    }



}
