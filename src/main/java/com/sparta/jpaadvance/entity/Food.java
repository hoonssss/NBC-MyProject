package com.sparta.jpaadvance.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "food")
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;

    @ManyToMany
    @JoinTable(name = "orders", //중간테이블 생성
            joinColumns = @JoinColumn(name = "food_id"), //현재 위치에서 중간 테이블 조인
            inverseJoinColumns = @JoinColumn(name = "user_id")) //반대 위치에서 중간 테이블 조인
    private List<User> userList = new ArrayList<>();
}