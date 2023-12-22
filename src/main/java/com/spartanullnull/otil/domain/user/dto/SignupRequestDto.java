package com.spartanullnull.otil.domain.user.dto;

import com.spartanullnull.otil.domain.user.entity.UserRoleEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class SignupRequestDto {

    @Pattern(regexp = "^[a-z0-9]{4,10}$")
    private String accountId;

    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()_+\\-=\\[\\]{};':\",./<>?\\\\|]{8,15}$")
    private String password;

    @NotNull
    private String nickname;

    @Email
    private String email;

    @NotNull
    private boolean admin = false;

    @NotNull
    private String adminToken = "";

    public SignupRequestDto(String accountId, String password, String nickname, String email) {
        this.accountId = accountId;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
    }
}