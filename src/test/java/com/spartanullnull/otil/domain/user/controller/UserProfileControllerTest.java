//package com.spartanullnull.otil.domain.user.controller;
//
//<<<<<<< HEAD
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//
//import com.spartanullnull.otil.domain.user.entity.User;
//import com.spartanullnull.otil.domain.user.service.UserProfileService;
//import com.spartanullnull.otil.security.Impl.UserDetailsImpl;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//@WebMvcTest(controllers = UserProfileControllerTest.class)
////@ContextConfiguration(classes = {SecurityConfig.class})
////@ContextConfiguration(classes = {WebSecurityConfig.class, SecurityConfig.class})
//=======
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//import com.fasterxml.jackson.databind.*;
//import com.spartanullnull.otil.domain.*;
//import com.spartanullnull.otil.domain.user.dto.*;
//import com.spartanullnull.otil.domain.user.service.*;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.*;
//import org.springframework.boot.test.autoconfigure.web.servlet.*;
//import org.springframework.boot.test.mock.mockito.*;
//import org.springframework.http.*;
//import org.springframework.security.core.context.*;
//import org.springframework.test.web.servlet.*;
//import org.springframework.test.web.servlet.request.*;
//import org.springframework.test.web.servlet.setup.*;
//import org.springframework.web.context.*;
//
//@WebMvcTest(controllers = UserProfileController.class)
////@TestPropertySource(locations = "classpath:application-test.yml")
//>>>>>>> 42f272f192f47c97db79fd7dec3dc08d45bc26c2
//class UserProfileControllerTest {
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @MockBean
//<<<<<<< HEAD
//=======
//    @Autowired
//>>>>>>> 42f272f192f47c97db79fd7dec3dc08d45bc26c2
//    UserProfileService userProfileService;
//
//    @Autowired
//    WebApplicationContext webApplicationContext;
//
//    @BeforeEach
//    void setUp() {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
//<<<<<<< HEAD
//            .apply(SecurityMockMvcConfigurers.springSecurity()).build();
//    }//security 기능 사용하여 애플리케이션 test setup
//
//    @Test
//    @WithMockUser
//    @DisplayName("PUT : 프로필 수정하기 요청을 처리합니다.")
//    public void PUT_프로필수정_핸들링() {
//        // GIVEN
//        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
//        User user = mock(User.class);
//        when(userDetails.getUser()).thenReturn(user);
//
//        // WHEN
//        String accountId = userDetails.getUser().getAccountId();
//
//        // THEN
//        System.out.println("accountId = " + accountId);
//=======
//            .apply(springSecurity())
//            .build();
//        this.mockMvc = MockMvcBuilders.standaloneSetup(
//                new UserProfileController(userProfileService))
//            .build();
//    }
//
//    @Test
//    @DisplayName("GET : 프로필 조회하기 요청을 처리합니다.")
//    public void GET_프로필조회_핸들링() throws Exception {
//        // GIVEN
//        Long userId = 1L;
//        // WHEN
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/profile"))
//            // THEN
//            .andExpect(status().isOk());
//    }
//
//    @Test
//    @WithMockCustomUser
//    @DisplayName("PUT : 프로필 수정하기 요청을 처리합니다.")
//    public void PUT_프로필수정_핸들링() throws Exception {
//        // GIVEN
//        Long userId = 1L;
//        // WHEN
//        mockMvc.perform(
//                MockMvcRequestBuilders
//                    .put("/api/users/profile")
//                    .content(new ObjectMapper().writeValueAsString(
//                        new UserProfileModifyResponseDto(
//                            "qwer1234",
//                            "qwer1234!@#",
//                            "jihoon123",
//                            "jihoon123@gmail.com",
//                            false
//                        )
//                    ))
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .accept(MediaType.APPLICATION_JSON)
//            )
//            // THEN
//            .andExpect(status().isOk());
//    }
//
//    @Test
//    @WithMockCustomUser
//    @DisplayName("로그인 사용자의 인증정보를 가져옵니다.")
//    public void 로그인사용자_인증정보() throws Exception {
//        // THEN
//        SecurityContext context = SecurityContextHolder.getContext();
//        System.out.println(
//            "context.getAuthentication().getPrincipal().getClass() = " + context.getAuthentication()
//                .getPrincipal().getClass());
////        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
//
//        // WHEN
////        assertEquals("abc123", userDetails.getUser().getAccountId());
//>>>>>>> 42f272f192f47c97db79fd7dec3dc08d45bc26c2
//    }
//}