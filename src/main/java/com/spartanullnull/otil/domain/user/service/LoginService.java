package com.spartanullnull.otil.domain.user.service;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.spartanullnull.otil.domain.user.dto.LoginRequestDto;
import com.spartanullnull.otil.domain.user.entity.QUser;
import com.spartanullnull.otil.domain.user.entity.User;
import com.spartanullnull.otil.domain.user.entity.UserRoleEnum;
import com.spartanullnull.otil.domain.user.repository.UserRepository;
import com.spartanullnull.otil.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void login(LoginRequestDto requestDto, HttpServletResponse response) {
        String accountId = requestDto.getAccountId();

        QUser qUser = QUser.user;
        BooleanExpression userPredicate = qUser.accountId.eq(accountId);

        User user = userRepository.findOne(userPredicate).orElseThrow(
            () -> new NullPointerException("등록된 ID가 존재하지 않습니다.")
        );

        if (passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            UserRoleEnum role = user.getRole();
            response.setHeader(JwtUtil.AUTHORIZATION_HEADER,
                jwtUtil.createToken(user.getAccountId(), role));
        }
    }
}

//    public void login(LoginRequestDto requestDto, HttpServletResponse response) {
//        String accountId = requestDto.getAccountId();
//
//        User user = userRepository.findByAccountId(accountId).orElseThrow(
//            () -> new UsernameNotFoundException("등록된 accountId가 존재하지 않습니다.")
//        );
//
//        if(passwordEncoder.matches(requestDto.getPassword(),user.getPassword())){
//            UserRoleEnum role = user.getRole();
//            response.setHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getAccountId(), role));
//        }
//    }
//}

//    public String login(String accountId, String password) {
//        try {
//            User user = userRepository.findByAccountIdAndPassword(accountId, passwordEncoder.encode(password)).orElseThrow(
//                () -> new UsernameNotFoundException("회원정보 불일치")
//            );
//            if (passwordEncoder.matches(password, user.getPassword())) {
//                UserRoleEnum role = user.getRole();
//                return jwtUtil.createToken(accountId, role);
//            } else {
//                throw new RuntimeException("잘못된 유저 정보");
//            }
//        } catch (UsernameNotFoundException e) {
//            throw new RuntimeException("사용자를 찾을 수 없습니다.");
//        }
//        catch (Exception e) {
//            throw new RuntimeException("로그인에 실패했습니다.", e);
//        }
