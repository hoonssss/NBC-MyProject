package com.example.backendgram.comment.dto;

import com.example.backendgram.comment.entity.Comment;
import com.example.backendgram.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDto {

    private Long id;
    private String text;
    private User user;
    private LocalDateTime createDate;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.text = comment.getText();
        this.user = new User(comment.getUser());
        this.createDate = comment.getLocalDateTime();
    }
}
