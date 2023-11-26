package com.example.backendgram.profile.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileRequestDto {

    private Long id;
    private String introduction;
    private String password;
    private boolean passwordUpdateRequired;
}
