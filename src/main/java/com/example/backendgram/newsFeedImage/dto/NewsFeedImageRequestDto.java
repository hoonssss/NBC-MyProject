package com.example.backendgram.newsFeedImage.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class NewsFeedImageRequestDto {

    private List<MultipartFile> files;

}
