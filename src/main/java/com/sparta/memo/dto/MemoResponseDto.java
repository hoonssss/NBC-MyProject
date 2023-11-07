package com.sparta.memo.dto;

import com.sparta.memo.entity.Memo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemoResponseDto {
    private Long id;
    private String username;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public MemoResponseDto(Memo memo) {
        this.id = memo.getId();
        this.username = memo.getUsername();
        this.contents = memo.getContents();
        this.createdAt = memo.getCreatedAt();
        this.modifiedAt = memo.getModifiedAt();
    }

//    Jdbc때 사용
//    public MemoResponseDto(Long id, String username, String contents) {
//        this.id = id;
//        this.username = username;
//        this.contents = contents;
//    }
}