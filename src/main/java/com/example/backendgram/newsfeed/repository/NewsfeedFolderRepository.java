package com.example.backendgram.newsfeed.repository;

import com.example.backendgram.folder.entity.Folder;
import com.example.backendgram.folder.entity.NewsfeedFolder;
import com.example.backendgram.newsfeed.Entity.NewsFeed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsfeedFolderRepository extends JpaRepository<NewsfeedFolder, Long> {
    Optional<NewsfeedFolder> findByNewsFeedAndFolder(NewsFeed newsFeed, Folder folder);
}
