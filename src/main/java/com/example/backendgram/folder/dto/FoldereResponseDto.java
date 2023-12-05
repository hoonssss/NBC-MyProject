package com.example.backendgram.folder.dto;

import com.example.backendgram.folder.entity.Folder;
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