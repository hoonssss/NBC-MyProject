package com.example.backendgram.folder.service;

import com.example.backendgram.folder.dto.FoldereResponseDto;
import com.example.backendgram.folder.entity.Folder;
import com.example.backendgram.folder.repository.FolderRepository;
import com.example.backendgram.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;

    public void addFolders(List<String> folderNames, User user) {
        if(folderNames == null){
            throw new IllegalArgumentException("1");
        }
        List<Folder> existFolderList = folderRepository.findAllByUserAndNameIn(user,folderNames);
        List<Folder> folderList = new ArrayList<>();

        for (String folderName : folderNames) { //중복 확인
            if(folderName != null) {

                if (!isExistFolderName(folderName, existFolderList)) {
                    Folder folder = new Folder(folderName, user);
                    folderList.add(folder);
                } else {
                    throw new IllegalArgumentException("폴더명이 중복되었습니다.");
                }
            }else{
                throw new IllegalArgumentException("2");
            }
        }
        folderRepository.saveAll(folderList);
    }

    public List<FoldereResponseDto> getFolders(User user) {
        List<Folder> folderList = folderRepository.findAllByUser(user);
        List<FoldereResponseDto> responseDtoList = new ArrayList<>();
        for (Folder folder : folderList) {
            responseDtoList.add(new FoldereResponseDto(folder));
        }
        return responseDtoList;
    }

    private boolean isExistFolderName(String folderName, List<Folder> existFolderList) { //중복 확인 메서드
        for (Folder existFolder : existFolderList) {
            if(folderName.equals(existFolder.getName())){
                return true;
            }
        }
        return false;
    }
}
