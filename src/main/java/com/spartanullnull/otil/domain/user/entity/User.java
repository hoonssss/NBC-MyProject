package com.spartanullnull.otil.domain.user.entity;

import com.spartanullnull.otil.domain.reportpost.entity.ReportPost;
import com.spartanullnull.otil.domain.user.dto.UserProfileModifyRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
@DynamicUpdate
@DynamicInsert
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String accountId;

    @Column
    @NotNull
    private String password;

    @Column(nullable = false, unique = true)
    @NotNull
    private String nickname;

    @Column(nullable = false, unique = true)
    @NotNull
    private String email;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private UserRoleEnum role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReportPost> reportPost = new ArrayList<>();

    private Long kakaoId;

    public User(String accountId, String password, String nickname, String email, UserRoleEnum role) {
        this.accountId = accountId;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
    }

    public User(Long id, String encodedPassword, String nickname, String email, UserRoleEnum role) {
        this.kakaoId = id;
        this.password = encodedPassword;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
    }

    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }

    public void modifyByRequest(UserProfileModifyRequestDto userProfileModifyRequestDto,
        String password, UserRoleEnum role) {
        this.accountId = userProfileModifyRequestDto.accountId();
        this.password = password;
        this.nickname = userProfileModifyRequestDto.nickname();
        this.email = userProfileModifyRequestDto.email();
        this.role = role;
    }

    @Builder
    public User(String accountId, String password, UserRoleEnum role){
        this.accountId = accountId;
        this.password = password;
        this.role = role;
    }
}
