package com.sparta.boardApp.entity;

import com.sparta.boardApp.dto.PostAddRequestDto;
import com.sparta.boardApp.dto.PostUpdateRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)// 생성자를 통해서 값 변경 목적으로 접근하는 메시지들 차단
public class PostEntity extends TimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String title;
    @Column(nullable = false, length = 15)
    private String author;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, length = 500)
    private String contents;


    public PostEntity(PostAddRequestDto postAddRequestDto) {
        this.title = postAddRequestDto.getTitle();
        this.author = postAddRequestDto.getAuthor();
        this.password = postAddRequestDto.getPassword(); //암호화
        this.contents = postAddRequestDto.getContent();
    }

    public void update(PostUpdateRequestDto postUpdateRequestDto) {
        this.title = postUpdateRequestDto.getTitle();
        this.author = postUpdateRequestDto.getAuthor();
        this.contents = postUpdateRequestDto.getContent();
    }
}
