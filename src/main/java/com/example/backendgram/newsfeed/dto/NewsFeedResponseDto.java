package com.example.backendgram.newsfeed.dto;

import com.example.backendgram.CommonResponseDto;
import com.example.backendgram.newsfeed.Entity.NewsFeed;
import com.example.backendgram.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class NewsFeedResponseDto extends CommonResponseDto {

    private Long id;
    private String title;
    private String content;
    private User user;
    private LocalDateTime creatDate;
    private int likeCount;

    public NewsFeedResponseDto(NewsFeed newsFeed) {
        this.id = newsFeed.getId();
        this.title = newsFeed.getTitle();
        this.content = newsFeed.getContent();
        this.user = newsFeed.getUser();
        this.creatDate = newsFeed.getCreatDate();
    }
}
