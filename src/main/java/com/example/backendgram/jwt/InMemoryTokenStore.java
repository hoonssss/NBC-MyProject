package com.example.backendgram.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InMemoryTokenStore implements TokenStore {

    private Map<String, String> refreshTokens = new ConcurrentHashMap<>();
    private Map<String, Date> refreshExpiration = new ConcurrentHashMap<>();

    private JwtUtil jwtUtil;

    @Override
    public void storeRefreshToken(String username, String refreshToken) {
        Date expiration = calculateExpirationDate(refreshToken);
        refreshTokens.put(username, refreshToken);
        refreshExpiration.put(username, expiration);
    }

    @Override
    public String getRefreshToken(String username) {
        String storedRefreshToken = refreshTokens.get(username);
        if (storedRefreshToken != null && isValidRefreshToken(username, storedRefreshToken)) {
            return storedRefreshToken;
        }
        return null;
    }

    @Override
    public void removeRefreshToken(String username) {
        refreshTokens.remove(username);
        refreshExpiration.remove(username);
    }

    @Override
    public boolean isValidRefreshToken(String username, String refreshToken) {
        Date storedExpiration = refreshExpiration.get(username);
        return storedExpiration != null && storedExpiration.after(new Date()) && refreshTokens.get(username).equals(refreshToken);
    }

    @Override
    public void updateRefreshTokenExpiration(String username, Date newExpiration) {
        refreshExpiration.put(username, newExpiration);
    }

    @Override
    public boolean hasRefreshToken(String username) {
        return refreshTokens.containsKey(username);
    }

    private Date calculateExpirationDate(String refreshToken) {
        Jws<Claims> refreshClaims = jwtUtil.parseRefreshToken(refreshToken);
        return refreshClaims.getBody().getExpiration();
    }
}
