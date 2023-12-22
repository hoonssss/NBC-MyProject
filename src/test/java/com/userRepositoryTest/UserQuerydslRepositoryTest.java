package com.userRepositoryTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.spartanullnull.otil.domain.user.dto.LoginRequestDto;
import com.spartanullnull.otil.domain.user.entity.QUser;
import com.spartanullnull.otil.domain.user.entity.User;
import com.spartanullnull.otil.domain.user.entity.UserRoleEnum;
import com.spartanullnull.otil.domain.user.repository.UserRepository;
import com.spartanullnull.otil.domain.user.service.LoginService;
import com.spartanullnull.otil.domain.user.service.SignupService;
import com.spartanullnull.otil.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;


public class UserQuerydslRepositoryTest {

    @Mock
    UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private SignupService signupService;

    @Mock
    private LoginService loginService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);

        String accountId = "test8020";
        String password = "test123!!";
        UserRoleEnum role = UserRoleEnum.USER;
        User user = User.builder().accountId(accountId).password(passwordEncoder.encode(password)).role(role).build();
        userRepository.save(user);
        when(userRepository.findByAccountId(accountId)).thenReturn(Optional.of(user));
    }

    @Test
    void testLogin(){
        //given
        String accountId = "test8020";
        String password = "test123!!";
        UserRoleEnum role = UserRoleEnum.USER;

        User user = User.builder().accountId(accountId).password(password).role(role).build();

        BooleanExpression userPredicate = QUser.user.accountId.eq(accountId);

        when(userRepository.findOne(any(BooleanExpression.class))).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, user.getPassword())).thenReturn(true);
        when(jwtUtil.createToken(accountId,user.getRole())).thenReturn("testToken");

        //when
        LoginRequestDto loginRequestDto = new LoginRequestDto(accountId, password);
        HttpServletResponse httpResponse = mock(HttpServletResponse.class);
        loginService.login(loginRequestDto,httpResponse);

        //then
        verify(userRepository, times(1)).findOne(userPredicate);
        verify(httpResponse, times(1)).setHeader(eq(JwtUtil.AUTHORIZATION_HEADER), eq("testToken"));
    }

}
