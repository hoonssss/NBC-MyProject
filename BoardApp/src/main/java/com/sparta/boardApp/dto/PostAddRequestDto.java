package com.sparta.boardApp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostAddRequestDto {

    private String title;
    private String author;
    private String password;
    private String content;

}
