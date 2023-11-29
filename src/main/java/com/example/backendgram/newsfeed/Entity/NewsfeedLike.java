package com.example.backendgram.newsfeed.Entity;

import com.example.backendgram.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class NewsfeedLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "newsfeed_id")
    private NewsFeed newsfeed;

    @ManyToOne
    @JoinColumn(name = "likes_id")
    private User user;

    public NewsfeedLike(User user, NewsFeed newsfeed){
        this.user = user;
        this.newsfeed = newsfeed;
    }
}
