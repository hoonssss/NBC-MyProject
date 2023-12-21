package com.spartanullnull.otil.domain.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalExceptionHandle {

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<RestApiException> illegalArgumentExceptionHandler(IllegalArgumentException e){
        RestApiException restApiException = new RestApiException(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(restApiException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<RestApiException> nullPointerExceptionHandel(NullPointerException e){
        RestApiException restApiException = new RestApiException(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(restApiException, HttpStatus.NOT_FOUND);
    }
}
