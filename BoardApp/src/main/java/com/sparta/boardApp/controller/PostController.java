package com.sparta.boardApp.controller;

import com.sparta.boardApp.dto.PostAddRequestDto;
import com.sparta.boardApp.dto.PostResponseDto;
import com.sparta.boardApp.dto.PostUpdateRequestDto;
import com.sparta.boardApp.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponseDto addPost(@RequestBody PostAddRequestDto postAddRequestDto){
        PostResponseDto postResponseDto = postService.addPost(postAddRequestDto);
        return postResponseDto;
    }

    @GetMapping("/{postId}")
    public PostResponseDto getPost(@PathVariable Long postId){
        return postService.getPost(postId);
    }

    @GetMapping
    public List<PostResponseDto> getPosts(){
        return postService.getPosts();
    }

    @PatchMapping("/{postId}")
    public PostResponseDto updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequestDto postUpdateRequestDto){
        return postService.updatePost(postId, postUpdateRequestDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT) //204
    @DeleteMapping("/{postId}")//delete메서드를 사용할때 Body에 데이터를 요청은 지양하는 편, @RequestHeader -> key값,받아올 변수이름
    public PostResponseDto deletePost(@PathVariable Long postId, @RequestHeader("password") String password){
        return postService.deletePost(postId, password);
    }

}
