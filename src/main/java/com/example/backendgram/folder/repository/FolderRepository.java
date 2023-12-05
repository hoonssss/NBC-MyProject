package com.example.backendgram.folder.repository;

import com.example.backendgram.folder.entity.Folder;
import com.example.backendgram.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder,Long> {
    List<Folder> findAllByUserAndNameIn(User user, List<String> folderNames);
    //select* from folder where user_id = 1 and name in ('1','2','3') -> user id 1이 (folder)name 1,2,3

    List<Folder> findAllByUser(User user);
   }
