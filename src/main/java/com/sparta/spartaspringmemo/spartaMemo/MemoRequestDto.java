package com.sparta.spartaspringmemo.spartaMemo;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemoRequestDto {
    private String title;
    private String username;
    private String password;
    private String contents;
    private LocalDateTime date;
}
