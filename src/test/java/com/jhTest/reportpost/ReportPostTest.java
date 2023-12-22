package com.jhTest.reportpost;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.spartanullnull.otil.domain.reportpost.controller.ReportPostController;
import com.spartanullnull.otil.domain.reportpost.dto.ReportPostRequestDto;
import com.spartanullnull.otil.domain.reportpost.dto.ReportPostResponseDto;
import com.spartanullnull.otil.domain.reportpost.service.ReportPostService;
import com.spartanullnull.otil.domain.user.entity.User;
import com.spartanullnull.otil.security.Impl.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class ReportPostTest {

    @Mock
    ReportPostService reportPostService;

    @InjectMocks
    ReportPostController reportPostController;

    @Mock
    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp(){
        when(passwordEncoder.matches(Mockito.any(), Mockito.any())).thenReturn(true);
    }

    @Test
    void testCreateReport() {
        //given
        ReportPostRequestDto requestDto = new ReportPostRequestDto();
        UserDetailsImpl userDetails = mock(UserDetailsImpl.class);
        User user = new User();

        when(userDetails.getUser()).thenReturn(user);

        ReportPostResponseDto reportPostResponseDto = new ReportPostResponseDto();

        when(reportPostService.createReport(requestDto, user)).thenReturn(reportPostResponseDto);

        //when
        ResponseEntity<ReportPostResponseDto> responseEntity = reportPostController.createReport(
            requestDto, userDetails);

        //then
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(reportPostResponseDto, responseEntity.getBody());
    }
}
