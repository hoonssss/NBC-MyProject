package com.example.backendgram.newsFeedImage.repository;

import com.example.backendgram.newsFeedImage.entity.NewsFeedImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsFeedImageRepository extends JpaRepository<NewsFeedImage,Long> {
}
