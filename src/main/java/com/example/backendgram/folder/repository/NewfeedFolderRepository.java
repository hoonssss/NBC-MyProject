package com.example.backendgram.folder.repository;

import com.example.backendgram.folder.entity.NewsfeedFolder;
import com.example.backendgram.folder.entity.Folder;
import com.example.backendgram.newsfeed.Entity.NewsFeed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewfeedFolderRepository extends JpaRepository<NewsfeedFolder, Long> {
    Optional<NewsfeedFolder> findByNewsFeedAndFolder(NewsFeed newsFeed, Folder folder);
}
