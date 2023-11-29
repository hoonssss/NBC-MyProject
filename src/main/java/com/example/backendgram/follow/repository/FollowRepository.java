package com.example.backendgram.follow.repository;

import com.example.backendgram.follow.entity.Follow;
import com.example.backendgram.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    void deleteByFollowerAndFollowing(User follower, User following);

    boolean existsByFollowerAndFollowing(User follower, User following);

    List<Follow> findByFollower(User user);

    List<Follow> findByFollowing(User user);
}
