package com.example.backendgram.jwt;

import com.example.backendgram.security.Impl.UserDetailsImpl;
import com.example.backendgram.user.entity.UserRoleEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import org.springframework.web.context.WebApplicationContext;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {
    // Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth";
    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";
    // 토큰 만료시간
    private final long TOKEN_TIME = 60 * 60 * 1000L; // 60분

    private final long REFRESH_TOKEN_EXPIRATION_TIME = 60 * 60 * 2000L; //120분

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    @Lazy
    private WebApplicationContext applicationContext;

    private UserDetailsService getUserDetailsService() {
        return applicationContext.getBean(UserDetailsService.class);
    }


    @Value("${jwt.secret.key}") // Base64 Encode 한 SecretKey
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @Value("${jwt.refresh.token.secret.key}")
    private String refreshSecretKey;
    private Key refreshKey;
    private final SignatureAlgorithm refreshSignatureAlgorithm = SignatureAlgorithm.HS256;


    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);

        byte[] refreshBytes = Base64.getDecoder().decode(refreshSecretKey);
        refreshKey = Keys.hmacShaKeyFor(refreshBytes);
    }

    // 토큰 생성
    public String createToken(String username, UserRoleEnum role) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username) // 사용자 식별자값(ID)
                        .claim(AUTHORIZATION_KEY, role) // 사용자 권한
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .signWith(key, signatureAlgorithm) // 암호화 알고리즘
                        .compact();
    }

    public String generateRefreshToken() {
        Date date = new Date();
        return BEARER_PREFIX +
            Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(refreshKey, refreshSignatureAlgorithm)
                .compact();
    }

    public Jws<Claims> parseRefreshToken(String refreshToken) {
        return Jwts.parserBuilder().setSigningKey(refreshKey).build().parseClaimsJws(refreshToken);
    }

    // header 에서 JWT 가져오기
    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    public Tokens refreshTokens(String refreshToken) {
        try {
            Jws<Claims> refreshClaims = parseRefreshToken(refreshToken);
            String username = refreshClaims.getBody().getSubject();

            if (tokenStore.isValidRefreshToken(username, refreshToken)) {
                UserDetailsImpl userDetails = (UserDetailsImpl) getUserDetailsService().loadUserByUsername(username);
                Tokens tokens = generateTokens(userDetails);
                tokenStore.updateRefreshTokenExpiration(username, new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME));
                return tokens;
            } else {
                throw new RuntimeException("Refresh token 유효하지 않습니다..");
            }
        } catch (JwtException e) {
            throw new RuntimeException("Refresh token 실패하였습니다..", e);
        }
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    public Tokens generateTokens(UserDetailsImpl userDetails) {
        String username = userDetails.getUsername();
        UserRoleEnum role = userDetails.getUser().getRole();
        String accessToken = createToken(username, role);
        String refreshToken = generateRefreshToken();
        tokenStore.storeRefreshToken(username, refreshToken);
        return new Tokens(accessToken, refreshToken);
    }

    public String generateRefreshToken(Long kakaoId) {
        Date date = new Date();
        return BEARER_PREFIX +
            Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .claim("kakao_id", kakaoId)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(refreshKey, refreshSignatureAlgorithm)
                .compact();
    }
}