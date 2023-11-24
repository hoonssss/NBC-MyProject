package com.sparta.myselectshop.controller;

import com.sparta.myselectshop.dto.FolderRequestDto;
import com.sparta.myselectshop.dto.FoldereResponseDto;
import com.sparta.myselectshop.security.UserDetailsImpl;
import com.sparta.myselectshop.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    @PostMapping("/folders")
    public void addFolders(@RequestBody FolderRequestDto folderRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        List<String> folderNames = folderRequestDto.getFolderNames();
        try {
            folderService.addFolders(folderNames,userDetails.getUser());
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("오류발생" + e.getMessage());
        }
    }

    @GetMapping("/folders")
    public List<FoldereResponseDto> getFolders(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return folderService.getFolders(userDetails.getUser());
    }
}
