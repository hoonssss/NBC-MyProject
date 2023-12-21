package com.spartanullnull.otil.domain.user.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestApiException {

    private String errorMessage;
    private long statusCode;

}
