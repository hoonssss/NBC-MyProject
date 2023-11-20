package com.sparta.jhboardapp.dto;

import com.sparta.jhboardapp.entity.BoardEntity;
import lombok.Getter;

import java.time.LocalDateTime;

public record ResponseDto (

     Long id,
     String title,
     String author,
     String content,
     LocalDateTime createdAt
)
{
    public ResponseDto(BoardEntity boardSave) {
        this(
                boardSave.getId(),
                boardSave.getTitle(),
                boardSave.getAuthor(),
                boardSave.getContent(),
                boardSave.getCreatedAt()
        );
    }
}
