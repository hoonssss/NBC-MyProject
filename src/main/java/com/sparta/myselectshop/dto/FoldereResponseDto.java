package com.sparta.myselectshop.dto;

import com.sparta.myselectshop.entity.Folder;
import lombok.Getter;

@Getter
public class
FoldereResponseDto {
    private Long id;
    private String name;

    public FoldereResponseDto(Folder folder) {
        this.id = folder.getId();
        this.name = folder.getName();
    }
}