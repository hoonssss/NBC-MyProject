package com.sparta.jpaadvance.relation;

import com.sparta.jpaadvance.entity.Food;
import com.sparta.jpaadvance.entity.User;
import com.sparta.jpaadvance.repository.FoodRepository;
import com.sparta.jpaadvance.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class ManyToManyTest {

    @Autowired
    FoodRepository foodRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    @Rollback(value = false)
    @DisplayName("N:M 단방향 테스트")
    void test1(){
        User user = new User();
        user.setName("Robbie");

        User user2 = new User();
        user2.setName("Robbert");

        Food food = new Food();
        food.setName("후라이드 치킨");
        food.setPrice(15000);
        food.getUserList().add(user);
        food.getUserList().add(user2);

        userRepository.save(user);
        userRepository.save(user2);
        foodRepository.save(food);
    }

}
