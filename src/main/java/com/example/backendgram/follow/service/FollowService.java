package com.example.backendgram.follow.service;

import com.example.backendgram.follow.entity.Follow;
import com.example.backendgram.follow.repository.FollowRepository;
import com.example.backendgram.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;


    @Transactional
    public void followUser(User follower, User following) {
        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);
        followRepository.save(follow);
    }

    @Transactional
    public void unfollowUser(User follower, User following) {
        followRepository.deleteByFollowerAndFollowing(follower, following);
    }

    public boolean isFollowing(User follower, User following) {
        return followRepository.existsByFollowerAndFollowing(follower, following);
    }

    public Set<User> getFollowingInfo(User user) {
        return followRepository.findByFollower(user).stream()
                .map(Follow::getFollowing)
                .collect(Collectors.toSet());
    }

    public Set<User> getFollowersInfo(User user) {
        return followRepository.findByFollowing(user).stream()
                .map(Follow::getFollower)
                .collect(Collectors.toSet());
    }
}
