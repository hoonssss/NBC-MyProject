package com.sparta.jhboardapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class JhBoardAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(JhBoardAppApplication.class, args);
    }

}
