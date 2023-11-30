package com.example.backendgram.newsFeedImage.entity;

import com.example.backendgram.newsfeed.Entity.NewsFeed;
import com.example.backendgram.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class NewsFeedImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String url;

    @ManyToOne
    @JoinColumn(name = "newsFeed_id")
    private NewsFeed newsFeed;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
