package com.example.backendgram.user.entity;

import com.example.backendgram.newsfeed.Entity.NewsFeed;
import com.example.backendgram.newsfeed.Entity.NewsfeedLike;
import com.example.backendgram.profile.entity.Profile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Profile profile;

    @OneToMany(mappedBy = "user")
    private List<NewsFeed> newsfeed;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<NewsfeedLike> likedNewsfeeds = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_follow",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private List<User> following = new ArrayList<>();

    @ManyToMany(mappedBy = "following")
    private List<User> followers = new ArrayList<>();

    private Long kakaoId;

    public User(String username, String password, String email, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public void likeNewsfeed(NewsFeed newsFeed){
        NewsfeedLike newsfeedLike = new NewsfeedLike(this,newsFeed);
        likedNewsfeeds.add(newsfeedLike);
        newsFeed.getNewsfeedLikes().add(newsfeedLike);
    }

    public void unlikeNewsfeed(NewsFeed newsfeed) {
        likedNewsfeeds.removeIf(like -> like.getNewsfeed().equals(newsfeed));
        newsfeed.getNewsfeedLikes().removeIf(like -> like.getUser().equals(this));
//        List<NewsfeedLike> newsfeedLikes = new ArrayList<>();
//        for(NewsfeedLike like : newsfeed.getNewsfeedLikes()){
//            if(!like.getUser().equals(this)){
//                newsfeedLikes.add(like);
//            }
//        }
//        newsfeed.setNewsfeedLikes(newsfeedLikes);
    }

    public User(User user) {
        this.username = user.getUsername();
    }

    public User(String username, String password, String email, UserRoleEnum role, Long kakaoId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.kakaoId = kakaoId;
    }

    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }
}