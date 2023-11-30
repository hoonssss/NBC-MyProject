package com.example.backendgram.newsfeed.dto;

import com.example.backendgram.CommonResponseDto;
import com.example.backendgram.newsFeedImage.entity.NewsFeedImage;
import com.example.backendgram.newsfeed.Entity.NewsFeed;
import com.example.backendgram.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<String> imageUrls;

    public NewsFeedResponseDto(NewsFeed newsFeed) {
        this.id = newsFeed.getId();
        this.title = newsFeed.getTitle();
        this.content = newsFeed.getContent();
        this.user = newsFeed.getUser();
        this.creatDate = newsFeed.getCreatDate();
        this.imageUrls = newsFeed.getImages().stream().map(
                NewsFeedImage::getUrl
        ).collect(Collectors.toList());
    }
}
