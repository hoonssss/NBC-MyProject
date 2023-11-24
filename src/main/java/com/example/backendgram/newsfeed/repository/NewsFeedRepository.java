package com.example.backendgram.newsfeed.repository;

import com.example.backendgram.newsfeed.Entity.NewsFeed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsFeedRepository extends JpaRepository<NewsFeed, Long> {



}
