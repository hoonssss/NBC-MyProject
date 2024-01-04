package com.example.backendgram.user.service;

import com.example.backendgram.jwt.JwtUtil;
import com.example.backendgram.jwt.Tokens;
import com.example.backendgram.security.Impl.UserDetailsImpl;
import com.example.backendgram.user.dto.SignupRequestDto;
import com.example.backendgram.user.entity.User;
import com.example.backendgram.user.entity.UserRoleEnum;
import com.example.backendgram.user.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // ADMIN_TOKEN
    @Value("${ADMIN_TOKEN}")
    private String ADMIN_TOKEN;

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // email 중복확인
        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = new User(username, password, email, role);
        userRepository.save(user);
    }

    public Tokens login(String username, String password) {
        try {
            User user = getUser(username, password);

            if (passwordEncoder.matches(password, user.getPassword())) {
                // 비밀번호가 일치하면 JWT 토큰을 생성하여 반환
                UserRoleEnum role = user.getRole();
                UserDetailsImpl userDetails = new UserDetailsImpl(user);
                Tokens tokens = jwtUtil.generateTokens(userDetails);

                return tokens;
            } else {
                throw new RuntimeException("잘못된 인증 정보입니다.");
            }
        } catch (UsernameNotFoundException e) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        } catch (Exception e) {
            throw new RuntimeException("로그인에 실패했습니다.", e);
        }
    }

    @Transactional
    public Tokens refreshTokens(String refreshToken) {
        try {
            Tokens tokens = jwtUtil.refreshTokens(refreshToken);
            return tokens;
        } catch (RuntimeException e) {
            log.error("Failed to refresh tokens.", e);
            throw new RuntimeException("Failed to refresh tokens.", e);
        }
    }

    private void logout() {
        try {
            SecurityContextHolder.clearContext(); // 컨텍스트를 지움
            new SecurityContextLogoutHandler().logout(null, null, null);
        } catch (Exception e) {
            throw new RuntimeException("로그아웃 실패.", e);
        }
    }

    @Transactional
    public void deleteAccount(String username, String password) {
        try {
            User user = getUser(username, password);
            if (user != null && user.getUsername().equals(username) && passwordEncoder.matches(
                password, user.getPassword())) {
                userRepository.delete(user);
                logout();
            }
        } catch (Exception e) {
            log.error("에러 발생 : " + username, e);
            throw e;
        }
    }

    private User getUser(String username, String password) {
        User user = userRepository.findByUsernameAndPassword(username, password).orElseThrow(
            () -> new UsernameNotFoundException("사용자를 찾을 수 없습니다.")
        );
        return user;
    }
}
