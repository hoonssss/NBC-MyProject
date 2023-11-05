package com.sparta.spartaspringmemo.spartaMemo;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemoResponseDto {
    private String title;
    private String username;
    private String contents;
    private LocalDateTime date;

    public MemoResponseDto(Memo memo) {
        this.username = memo.getUsername();
        this.contents = memo.getContents();
    }

    public MemoResponseDto(String username, String title, String contents, LocalDateTime date) {
        this.username = username;
        this.title = title;
        this.contents = contents;
        this.date = date;
    }
}

