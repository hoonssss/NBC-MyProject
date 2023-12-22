package com.jhTest.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.spartanullnull.otil.domain.user.dto.SignupRequestDto;
import com.spartanullnull.otil.domain.user.entity.User;
import com.spartanullnull.otil.domain.user.entity.UserRoleEnum;
import com.spartanullnull.otil.domain.user.repository.UserRepository;
import com.spartanullnull.otil.domain.user.service.SignupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class UserSignupTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    SignupService signupService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        signupService = new SignupService(userRepository, passwordEncoder);
    }

    @Test
    @DisplayName("회원가입")
    void signup() {

        //given
        User user = new User("accountId", "asdg124!@%", "jihoon", "sdf123@gmail.com",
            UserRoleEnum.USER);

        SignupRequestDto signupRequestDto = new SignupRequestDto(user.getAccountId(),
            user.getPassword(), user.getNickname(), user.getEmail());

        String accountId = signupRequestDto.getAccountId();;
        String password = signupRequestDto.getPassword();

        //when
        signupService.singup(signupRequestDto);

        //then
        assertEquals(accountId, userRepository.findByAccountId(accountId));

    }
}
