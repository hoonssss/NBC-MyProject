package com.example.backendgram.newsfeed.repository;

import com.example.backendgram.newsfeed.Entity.NewsFeed;
import com.example.backendgram.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsFeedRepository extends JpaRepository<NewsFeed, Long> {

    Page<NewsFeed> findAllByUserAndNewsfeedFolders_FolderId(User user, Long folderId, Pageable pageable);
}
