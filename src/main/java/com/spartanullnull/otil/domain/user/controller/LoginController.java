package com.spartanullnull.otil.domain.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spartanullnull.otil.domain.user.dto.LoginRequestDto;
import com.spartanullnull.otil.domain.user.service.KakaoService;
import com.spartanullnull.otil.domain.user.service.LoginService;
import com.spartanullnull.otil.global.dto.ApiResponseDto;
import com.spartanullnull.otil.global.dto.CommonResponseDto;
import com.spartanullnull.otil.jwt.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final KakaoService kakaoService;

    @PostMapping("/users/login")
    public ResponseEntity<ApiResponseDto> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        try {
            loginService.login(requestDto,response);

            ApiResponseDto apiResponseDto = new ApiResponseDto();
            apiResponseDto.setDate(requestDto.getAccountId());
            apiResponseDto.setMsg("로그인 성공");
            apiResponseDto.setStatusCode(HttpStatus.OK.value());
            return ResponseEntity.ok().body(apiResponseDto);
        }catch (IllegalArgumentException e){
            ApiResponseDto apiResponseDto = new ApiResponseDto();
            apiResponseDto.setMsg("로그인 실패");
            apiResponseDto.setStatusCode(HttpStatus.BAD_REQUEST.value());
            return ResponseEntity.badRequest().body(apiResponseDto);
        }
    }

    @PostMapping("/users/logout")
    public ResponseEntity<CommonResponseDto> logout(HttpServletRequest request,
        HttpServletResponse response) {
        try {
            //인증 정보 조회
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null) {
                //로그아웃 핸들러를 통해 인증 정보 및 사용자 세션 삭제
                new SecurityContextLogoutHandler().logout(request, response, auth);
                return ResponseEntity.ok()
                    .body(new CommonResponseDto("로그아웃 성공", HttpStatus.OK.value()));
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(new CommonResponseDto("로그아웃 실패", HttpStatus.BAD_REQUEST.value()));
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/kakao/callback")
    public ResponseEntity<CommonResponseDto> kakaoLogin(@RequestParam String code,
        HttpServletResponse response)
        throws JsonProcessingException {
        try {
            String token = kakaoService.kakaoLogin(code);

            Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token.substring(7));
            cookie.setPath("/");
            response.addCookie(cookie);

            return ResponseEntity.ok()
                .body(new CommonResponseDto("Kakao 로그인 성공", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(new CommonResponseDto("Kakao 로그인 실패", HttpStatus.BAD_REQUEST.value()));
        }
    }
}