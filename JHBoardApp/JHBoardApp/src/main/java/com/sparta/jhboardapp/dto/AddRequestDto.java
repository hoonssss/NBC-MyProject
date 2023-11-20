package com.sparta.jhboardapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AddRequestDto {

    private String title;
    private String author;
    private String password;
    private String content;

}
