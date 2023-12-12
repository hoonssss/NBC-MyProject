package com.example.backendgram.newsfeed.Entity;

import com.example.backendgram.comment.entity.Comment;
import com.example.backendgram.folder.entity.NewsfeedFolder;
import com.example.backendgram.newsFeedImage.entity.NewsFeedImage;
import com.example.backendgram.newsfeed.dto.NewsFeedRequestDto;
import com.example.backendgram.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
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

    @OneToMany(mappedBy = "newsFeed")
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "newsfeed")
    private List<NewsfeedLike> newsfeedLikes = new ArrayList<>();

    @OneToMany(mappedBy = "newsFeed", cascade = CascadeType.REMOVE)
    @OrderBy("id asc")
    private List<NewsFeedImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "newsfeedFolder")
    private List<NewsfeedFolder> newsfeedFolders = new ArrayList<>();

    public NewsFeed(NewsFeedRequestDto newsFeedRequestDto) {
        this.title = newsFeedRequestDto.getTitle();
        this.content = newsFeedRequestDto.getContent();
        this.creatDate = LocalDateTime.now();
    }

    public List<User> getLikes() {
        return newsfeedLikes.stream().map(
                NewsfeedLike::getUser
        ).collect(Collectors.toList());
    }
}
