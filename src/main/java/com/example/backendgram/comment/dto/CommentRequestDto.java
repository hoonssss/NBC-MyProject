package com.example.backendgram.comment.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {

    private Long NewsFeedId;
    private String password;
    private String text;

}
