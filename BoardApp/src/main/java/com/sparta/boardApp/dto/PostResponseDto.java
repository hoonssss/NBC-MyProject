package com.sparta.boardApp.dto;

import com.sparta.boardApp.entity.PostEntity;

import java.time.LocalDateTime;

public record PostResponseDto ( //record -> @Getter 사용 X 보안상 record 사용

    Long id,
    String title,
    String author,
    String content,
    LocalDateTime createdAt
){
    public PostResponseDto(PostEntity savePost) {
        this(
                savePost.getId(),
                savePost.getTitle(),
                savePost.getAuthor(),
                savePost.getContents(),
                savePost.getCreatedAt()
        );
    }
}
