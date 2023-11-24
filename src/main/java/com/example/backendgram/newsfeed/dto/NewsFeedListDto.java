package com.example.backendgram.newsfeed.dto;

import com.example.backendgram.newsfeed.repository.NewsFeedRepository;
import com.example.backendgram.user.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NewsFeedListDto {

    private User user;
    private List<NewsFeedRepository> newsFeedRepositories;


}
