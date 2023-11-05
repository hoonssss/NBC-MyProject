package com.sparta.spartaspringmemo.spartaMemo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemoRequestDto {
    private String title;
    private String username;
    private String password;
    private String contents;
    private LocalDateTime date;
}
