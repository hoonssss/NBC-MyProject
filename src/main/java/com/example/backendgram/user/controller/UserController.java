package com.example.backendgram.user.controller;


import com.example.backendgram.CommonResponseDto;
import com.example.backendgram.folder.service.FolderService;
import com.example.backendgram.jwt.JwtUtil;
import com.example.backendgram.kakao.KakaoService;
import com.example.backendgram.security.Impl.UserDetailsImpl;
import com.example.backendgram.user.dto.SignupRequestDto;
import com.example.backendgram.user.dto.UserInfoDto;
import com.example.backendgram.user.entity.UserRoleEnum;
import com.example.backendgram.user.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.Cookie;
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
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final FolderService folderService;
    private final UserService userService;
    private final KakaoService kakaoService;

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
            return "redirect:/error";
        }
    }

    //Kakao
    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        String token = kakaoService.kakaoLogin(code);

        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER,token.substring(7));
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/";
    }

    @GetMapping("/user-folder")
    public String getUserInfo(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails){ //동적 처리를 위해 Model 사용
        model.addAttribute("folders",folderService.getFolders(userDetails.getUser()));//body -> json 전달
        return "index :: #fragment"; //추가로 데이터를 줌
    }
}