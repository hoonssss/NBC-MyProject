package com.spartanullnull.otil.domain.reportpost.entity;

import com.spartanullnull.otil.domain.reportpost.dto.ReportPostRequestDto;
import com.spartanullnull.otil.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table
@Entity
@NoArgsConstructor
public class ReportPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private String title;

    @Column
    @NotNull
    private String content;
    
    @Column
    private String imageUrl;

    public ReportPost(ReportPostRequestDto requestDto) {
        this.id = requestDto.getId();
        this.name = requestDto.getName();
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setContent(String content){
        this.content = content;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
