package com.sparta.boardApp.dto;

import lombok.Getter;

@Getter
public class PostUpdateRequestDto {
    private String title;
    private String author;
    private String password;
    private String content;
}
