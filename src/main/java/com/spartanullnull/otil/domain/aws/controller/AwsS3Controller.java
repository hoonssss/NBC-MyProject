package com.spartanullnull.otil.domain.aws.controller;

import com.spartanullnull.otil.domain.aws.service.AwsS3Service;
import com.spartanullnull.otil.global.dto.CommonResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/s3")
public class AwsS3Controller {

    private final AwsS3Service awsS3Service;

    @PostMapping("/upload-image")
    public ResponseEntity<List<String>> uploadImage(
        @RequestParam("files") List<MultipartFile> files) {
        List<String> fileNames = awsS3Service.uploadImage(files);
        return ResponseEntity.ok().body(fileNames);
    }

    @DeleteMapping("/delete-image/{fileName}")
    public ResponseEntity<CommonResponseDto> deleteImage(
        @PathVariable String fileName) {
        awsS3Service.deleteImage(fileName);
        return ResponseEntity.ok().body(new CommonResponseDto("Image deleted successfully", HttpStatus.OK.value()));
    }

}
