package com.spartanullnull.otil.domain.post.dto;

import com.spartanullnull.otil.domain.category.entity.*;
import com.spartanullnull.otil.domain.post.entity.*;
import com.spartanullnull.otil.global.dto.*;
import java.time.*;
import java.util.*;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto extends CommonResponseDto {

    private Long id;
    private String accountId;
    private String title;
    private String content;
    private List<String> categoryList;
    private LocalDateTime createdAt;

    public static PostResponseDto of(Post post, List<Category> categoryList) {
        return PostResponseDto.builder()
            .id(post.getId())
            .accountId(post.getUser().getAccountId())
            .title(post.getTitle())
            .content(post.getContent())
            .categoryList(
                categoryList.stream().map(Category::getCategoryName).toList())
            .createdAt(post.getCreatedAt())
            .build();
    }

    public static PostResponseDto of(Post post) {
        return PostResponseDto.builder()
            .id(post.getId())
            .accountId(post.getUser().getAccountId())
            .title(post.getTitle())
            .content(post.getContent())
            .categoryList(
                post.getPostCategories()
                    .stream()
                    .filter(postCategory -> postCategory.getPost().equals(post))
                    .map(postCategory -> postCategory.getCategory().getCategoryName())
                    .toList()
            )
            .createdAt(post.getCreatedAt())
            .build();
    }

}
