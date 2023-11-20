package com.sparta.jhboardapp.service;

import com.sparta.jhboardapp.dto.LoginReqeustDto;
import com.sparta.jhboardapp.dto.SignupRequestDto;
import com.sparta.jhboardapp.entity.User;
import com.sparta.jhboardapp.entity.UserRoleEnum;
import com.sparta.jhboardapp.jwt.JwtUtil;
import com.sparta.jhboardapp.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtily;

//    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtily) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//        this.jwtUtily = jwtUtily;
//    }

    // ADMIN_TOKEN 일반사용자인지 관리자인지 구분하기 위해
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

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
        if (requestDto.isAdmin()) { //boolean type -> is~ 붙이는게 규칙
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = new User(username, password, email, role);
        userRepository.save(user);
    }
    public void login(LoginReqeustDto loginReqeustDto, HttpServletResponse res) {
        String username = loginReqeustDto.getUsername();
        String password = loginReqeustDto.getPassword();
        //사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow( //값이 있다면 true, 없다면 예외
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );
        //패스워드 확인
        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        //인증확인 -> JWT생성과 쿠키에 저장하여 Response 객체에 저장
        //인증확인 -> JWT생성
        String token = jwtUtily.createToken(user.getUsername(), user.getRole());
        //쿠키에 저장하여 Response객체에 저장
        jwtUtily.addJwtToCookie(token,res);
    }
}