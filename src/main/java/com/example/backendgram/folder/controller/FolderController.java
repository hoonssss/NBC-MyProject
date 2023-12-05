package com.example.backendgram.folder.controller;

import com.example.backendgram.folder.service.FolderService;
import com.example.backendgram.folder.dto.FolderRequestDto;
import com.example.backendgram.folder.dto.FoldereResponseDto;
import com.example.backendgram.security.Impl.UserDetailsImpl;
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
