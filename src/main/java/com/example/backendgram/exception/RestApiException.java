package com.example.backendgram.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestApiException {

    private String msg;
    private Integer statusCode;

}
