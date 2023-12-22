package com.spartanullnull.otil.domain.user.repository;

import com.spartanullnull.otil.domain.user.entity.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.*;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User>{

    Optional<User> findByAccountId(String accountId);

    Optional<User> findByEmail(String email);
    
    Optional<User> findByNickname(String nickname);

    Optional<User> findByKakaoId(Long kakaoId);

    Optional<User> findByAccountIdAndPassword(String accountId, String encode);
}
