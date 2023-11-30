package com.example.backendgram.newsFeedImage.service;

import com.example.backendgram.newsFeedImage.entity.NewsFeedImage;
import com.example.backendgram.newsFeedImage.repository.NewsFeedImageRepository;
import com.example.backendgram.security.Impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NewsFeedImageService {

    private final NewsFeedImageRepository newsFeedImageRepository;

    @Value("${profile.image.directory}")
    private String imageDirectory;

    @Transactional
    public void setImage(UserDetailsImpl userDetails, MultipartFile image) {
        if (userDetails == null) {
            throw new IllegalArgumentException("잘못된 사용자 정보입니다.");
        }

        NewsFeedImage newsFeedImage = new NewsFeedImage();
        newsFeedImage.setUser(userDetails.getUser());

        if (image != null) {
            try {
                newsFeedImage.setUrl(saveImage(image));
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("이미지 저장에 실패했습니다.", e);
            }
        }

        newsFeedImageRepository.save(newsFeedImage);
    }

    // 이미지 저장 메서드
    private String saveImage(MultipartFile image) throws IOException {
        // 파일 타입 유효성 검사 ex 이미지인지
        if (!isImageFile(image)) {
            throw new IllegalArgumentException("잘못된 파일 유형입니다. 이미지 파일만 허용됩니다.");
        }

        String imageName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
        try {
            Files.copy(image.getInputStream(), Paths.get(imageDirectory, imageName),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("이미지 저장에 실패했습니다.", e);
        }

        return imageName;
    }

    // 이미지 파일 타입 확인 메서드 (예시: Apache Tika 사용)
    private boolean isImageFile(MultipartFile file) {
        // 내용 유형을 기반으로 파일 유형 확인 가능
        // Apache Tika와 같은 라이브러리를 사용하거나
        // 파일 확장자를 기반으로 요구사항에 맞게 수정
        // 예: return Files.probeContentType(Paths.get(file.getOriginalFilename())).startsWith("image/");
        return true;
    }
}


