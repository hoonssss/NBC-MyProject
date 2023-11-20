package com.sparta.jhboardapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class UpdateRequestDto {

    private String title;
    private String author;
    private String password;
    private String content;

}
