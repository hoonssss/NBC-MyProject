package com.sparta.boardApp.service;

import com.sparta.boardApp.dto.PostAddRequestDto;
import com.sparta.boardApp.dto.PostResponseDto;
import com.sparta.boardApp.dto.PostUpdateRequestDto;
import com.sparta.boardApp.entity.PostEntity;
import com.sparta.boardApp.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostResponseDto addPost(PostAddRequestDto postAddRequestDto) {
        PostEntity postEntity = new PostEntity(postAddRequestDto);
        PostEntity savePost = postRepository.save(postEntity);
        return new PostResponseDto(savePost);
    }

    public PostResponseDto getPost(Long postId) {
        PostEntity postEntity = getPostEntity(postId);
        return new PostResponseDto(postEntity);
    }

    public List<PostResponseDto> getPosts() {
        return postRepository.findAllByOrderByCreatedAtDesc().stream().map(PostResponseDto::new).collect(Collectors.toList());
    }

//        return postRepository.findAll().stream()
//                .map(postEntity -> new PostResponseDto(postEntity))
//                .collect(Collectors.toList());
//    }
//        return postRepository.findAll().stream()
//                .map(postEntity -> {
//                    PostResponseDto postResponseDto = new PostResponseDto(postEntity);
//                    return postResponseDto;
//                }).collect(Collectors.toList());
//    }
//        List<PostResponseDto> responseDtoList = new ArrayList<>();
//        List<PostEntity> postEntityList = postRepository.findAll();
//        for (PostEntity postEntity : postEntityList){
//            PostResponseDto postResponseDto = new PostResponseDto(postEntity);
//            responseDtoList.add(postResponseDto);
//        }
//        return responseDtoList;
//    }
    @Transactional
    public PostResponseDto updatePost(Long postId, PostUpdateRequestDto postUpdateRequestDto) {
        PostEntity postEntity = getPostEntity(postId);
        mathingPassword(postUpdateRequestDto.getPassword(), postEntity);
        postEntity.update(postUpdateRequestDto);
        return new PostResponseDto(postEntity);
    }

    public PostResponseDto deletePost(Long postId, String password) {
        PostEntity postEntity = getPostEntity(postId);
        mathingPassword(password, postEntity);
        postRepository.delete(postEntity);
        return new PostResponseDto(postEntity);
    }

    private PostEntity getPostEntity(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(
                        () -> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다.")
                );
    }

    private static void mathingPassword(String password, PostEntity postEntity) {
        if(!postEntity.getPassword().equals(password)){
            throw new NullPointerException("비밀번호가 일치하지 않습니다.");
        }
    }
}
