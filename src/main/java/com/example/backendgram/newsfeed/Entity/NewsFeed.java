package com.example.backendgram.newsfeed.Entity;

import com.example.backendgram.newsfeed.dto.NewsFeedRequestDto;
import com.example.backendgram.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class NewsFeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private LocalDateTime creatDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public NewsFeed(NewsFeedRequestDto newsFeedRequestDto) {
        this.title = newsFeedRequestDto.getTitle();
        this.content = newsFeedRequestDto.getContent();
        this.creatDate = LocalDateTime.now();
    }

    public void setUser(User user){
        this.user = user;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
