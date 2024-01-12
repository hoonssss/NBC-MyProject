package com.example.backendgram.jwt;

import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public interface TokenStore {

    void storeRefreshToken(String username, String refreshToken);

    String getRefreshToken(String username);

    void removeRefreshToken(String username);

    boolean isValidRefreshToken(String username, String refreshToken);

    // 추가: 리프래시 토큰 만료 시간 갱신
    void updateRefreshTokenExpiration(String username, Date newExpiration);

    // 추가: 리프래시 토큰 저장 여부 확인
    boolean hasRefreshToken(String username);
}
