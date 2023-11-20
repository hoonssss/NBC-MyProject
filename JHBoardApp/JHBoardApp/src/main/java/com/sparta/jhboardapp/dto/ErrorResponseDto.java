package com.sparta.jhboardapp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ErrorResponseDto {

    private final Error error;


    public ErrorResponseDto(int status, String msg) {
        this.error = new Error(status,msg);
    }

    record Error(
            int status,
            @JsonProperty("message")
            String msg
    ){

    }
}
