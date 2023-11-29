package com.example.backendgram.user.controller;


import com.example.backendgram.CommonResponseDto;
import com.example.backendgram.jwt.JwtUtil;
import com.example.backendgram.security.UserDetailsImpl;
import com.example.backendgram.user.dto.SignupRequestDto;
import com.example.backendgram.user.dto.UserInfoDto;
import com.example.backendgram.user.entity.UserRoleEnum;
import com.example.backendgram.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @GetMapping("/user/login-page")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/user/signup")
    public String signupPage() {
        return "signup";
    }

    @GetMapping("/user/delete-account")
    public String deleteAccountPage() {
        return "delete-account";
    }

    @PostMapping("/user/signup")
    public String signup(@Valid SignupRequestDto requestDto, BindingResult bindingResult) {
        // Validation 예외처리
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if (fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return "redirect:/api/user/signup";
        }

        userService.signup(requestDto);
        return "redirect:/api/user/login-page";
    }

    @PostMapping("user/login")
    public ResponseEntity<CommonResponseDto> login(@RequestParam String username, @RequestParam String password) {
        try {
            userService.login(username, password);
            return ResponseEntity.ok().body(new CommonResponseDto("로그인 성공", HttpStatus.OK.value()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto("로그인 실패", HttpStatus.BAD_REQUEST.value()));
        }
    }

    // 회원 관련 정보 받기
    @GetMapping("/user-info")
    @ResponseBody
    public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null && userDetails.getUser() != null) {
            String username = userDetails.getUser().getUsername();
            UserRoleEnum role = userDetails.getUser().getRole();
            boolean isAdmin = (role == UserRoleEnum.ADMIN);

            return new UserInfoDto(username, isAdmin);
        } else {
            return new UserInfoDto("unknown", false);
        }
    }

    @PostMapping("/user/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            new SecurityContextLogoutHandler().logout(request,response,auth);
        }catch (Exception e){
            log.error("Logout failed.", e);
            throw new RuntimeException("로그아웃 실패.", e);
        }
        return "redirect:/api/user/login-page";
    }

    @PostMapping("/user/delete-account")
    public String deleteAccount(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam String password) {
        try {
            if (userDetails != null && userDetails.getUser() != null) {
                String username = userDetails.getUser().getUsername();
                userService.deleteAccount(username, password);
                return "redirect:/api/user/login-page";
            } else {
                return "redirect:/error";
            }
        } catch (Exception e) {
            log.error("로그아웃 중 오류 발생", e);
            return "redirect:/error";
        }
    }
}