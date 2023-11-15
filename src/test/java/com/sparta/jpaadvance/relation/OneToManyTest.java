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

import java.util.List;

@Transactional
@SpringBootTest
public class OneToManyTest {

    @Autowired
    FoodRepository foodRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("1:N 단방향")
    @Rollback(value = false)
    void test1(){

        User user = new User();
        user.setName("Robbie");

        User user2 = new User();
        user2.setName("Robbert");

        Food food = new Food();
        food.setName("후라이드치킨");
        food.setPrice(15000);
        food.getUserList().add(user);
        food.getUserList().add(user2);

        userRepository.save(user);
        userRepository.save(user2);
        foodRepository.save(food);
    }

    @Test
    @DisplayName("1대N 조회테스트")
    void test2(){
        Food food = foodRepository.findById(2L).orElseThrow(NullPointerException::new);
        System.out.println("food.getName() = " + food.getName());

        //해당 음식을 주문한 고객 정보
        List<User> users = food.getUserList();
        for(User user : users){
            System.out.println("user.getName() = " + user.getName());
        }
    }

}
