package com.spartanullnull.otil.domain.user.repository;

import com.spartanullnull.otil.domain.user.entity.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.*;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User>{

    @Query("select u from User u where u.accountId = :accountId")
    Optional<User> findByAccountId(String accountId);

    @Query("select u from User u where u.email = :email")
    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.nickname = :nickname")
    Optional<User> findByNickname(String nickname);

    @Query("select u from User u where u.kakaoId = :kakaoId")
    Optional<User> findByKakaoId(Long kakaoId);

    Optional<User> findByAccountIdAndPassword(String accountId, String encode);
}
