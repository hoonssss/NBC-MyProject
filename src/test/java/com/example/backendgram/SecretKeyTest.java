package com.example.backendgram;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class SecretKeyTest {

    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
    private String secretKey;

    @Test
    void test(){
        assertThat(secretKey).isNotNull();
    }
}
