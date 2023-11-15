package com.sparta.jpaadvance.relation;

import com.sparta.jpaadvance.entity.Food;
import com.sparta.jpaadvance.entity.User;
import com.sparta.jpaadvance.repository.FoodRepository;
import com.sparta.jpaadvance.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ManyToOneTest {

    @Autowired
    FoodRepository foodRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    @Rollback(value = false)
    @DisplayName("N대1 단방향 테스트")
    void test1(){
        User user = new User();
        user.setName("Robbie");

        Food food = new Food();
        food.setName("후라이드 치킨");
        food.setPrice(15000);
        food.setUser(user);

        Food food2 = new Food();
        food2.setName("후라이드 치킨");
        food2.setPrice(15000);
        food2.setUser(user);

        userRepository.save(user);
        foodRepository.save(food);
        foodRepository.save(food2);
    }
}
