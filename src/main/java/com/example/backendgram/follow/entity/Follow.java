package com.example.backendgram.follow.entity;

import com.example.backendgram.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "following_id")
    private User following;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;
}
