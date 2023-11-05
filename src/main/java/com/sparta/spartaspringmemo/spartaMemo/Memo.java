package com.sparta.spartaspringmemo.spartaMemo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.annotation.ApplicationScope;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Memo {
    private Long id;
    private String title;
    private String username;
    private String password;
    private String contents;
    private LocalDateTime date;

    public Memo(MemoRequestDto memoRequestDto) {
        this.title = memoRequestDto.getTitle();
        this.username = memoRequestDto.getUsername();
        this.password = memoRequestDto.getPassword();
        this.contents = memoRequestDto.getContents();
        this.date = memoRequestDto.getDate();
    }

    public Memo(String title, String username, String password, String contents, LocalDateTime date) {
        this.title = title;
        this.username = username;
        this.password = password;
        this.contents = contents;
        this.date = date;
    }

    public void update(MemoRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.title = requestDto.getTitle();
        this.password = requestDto.getPassword();
        this.contents = requestDto.getContents();
        this.date = requestDto.getDate();
    }
}
