package com.example.backendgram.follow.dto;

import com.example.backendgram.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FollowResponseDto {

    private User following;
    private User follower;

}
