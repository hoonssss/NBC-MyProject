package com.spartanullnull.otil.domain.category.service;

import com.spartanullnull.otil.domain.category.entity.*;
import com.spartanullnull.otil.domain.category.repository.*;
import com.spartanullnull.otil.domain.post.entity.*;
import java.util.*;
import java.util.function.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * 카테고리 문자열 리스트로부터 카테고리 엔티티 생성 및 저장
     *
     * @param categoryInputs 카테고리 입력 문자열들
     * @param post           게시글 엔티티
     * @return 게시글과 연관된 카테고리들 반환
     * @author 임지훈
     */
    public List<Category> buildAndSaveCategoriesByRequest(List<String> categoryInputs,
        Post post) {
        // 카테고리 문자열 리스트 -> 카테고리 엔티티 리스트
        List<Category> categories = getCategoriesByRequest(categoryInputs);

        // 기존 카테고리가 아닌 카테고리들에 대해 저장
        categories.forEach(
            category -> {
                if (categoryRepository.findByCategoryName(category.getCategoryName()).isEmpty()) {
                    categoryRepository.save(category);
                    category.updatePost(post);
                }
            }
        );

        // 기존 + 새로 저장된 카테고리들 반환
        return categories;
    }

    public List<Category> getNotExistingCategories(List<Category> categories) { // C#, Python
        return categories.stream()
            .filter(category ->
                categoryRepository
                    .findByCategoryName(category.getCategoryName())
                    .isEmpty()
            ).toList();
    }

    /**
     * 입력문자열 리스트 로부터 카테고리 엔티티 리스트 생성
     *
     * @param categoryInputs 입력문자열 리스트
     * @return 카테고리 엔티티 리스트
     */
    public List<Category> getCategoriesByRequest(List<String> categoryInputs) {
        if (categoryInputs == null) {
            throw new IllegalArgumentException("카테고리가 없습니다.");
        }

        return categoryInputs.stream()
            .map(buildCategoryByRequest())
            .toList();
    }

    /**
     * 입력문자열로부터 카테고리 엔티티 생성
     *
     * @return 카테고리 엔티티
     */
    private Function<String, Category> buildCategoryByRequest() {
        return categoryInput ->
            Category.builder()
                .categoryName(categoryInput)
                .build();
    }
}