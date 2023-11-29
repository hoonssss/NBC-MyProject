package com.example.backendgram.follow.repository;

import com.example.backendgram.follow.entity.Follow;
import com.example.backendgram.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    void deleteByFollowerIdAndFollowingId(User followerId, User followingId);

    boolean existsByFollowerAndFollowing(User follower, User following);
}
