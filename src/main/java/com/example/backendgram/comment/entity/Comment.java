package com.example.backendgram.comment.entity;

import com.example.backendgram.comment.dto.CommentRequestDto;
import com.example.backendgram.newsfeed.Entity.NewsFeed;
import com.example.backendgram.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter

@Setter
@Entity
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private String password;

    @Column
    private LocalDateTime localDateTime;

    @ManyToOne
    @JoinColumn(name = "newsFeed_id")
    private NewsFeed newsFeed;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(CommentRequestDto commentRequestDto) {
        this.text = commentRequestDto.getText();
        this.password = commentRequestDto.getPassword();
        this.localDateTime = LocalDateTime.now();
    }

    public void setUser(User user){
        this.user = user;
    }

    public void setNewsFeed(NewsFeed newsFeed){
        this.newsFeed = newsFeed;
        newsFeed.getCommentList().add(this);
    }

    public void setText(String text){
        this.text = text;
    }
}
