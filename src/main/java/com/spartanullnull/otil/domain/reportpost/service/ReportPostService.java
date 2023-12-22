package com.spartanullnull.otil.domain.reportpost.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.spartanullnull.otil.domain.reportpost.dto.PageRequestDto;
import com.spartanullnull.otil.domain.reportpost.dto.ReportPostRequestDto;
import com.spartanullnull.otil.domain.reportpost.dto.ReportPostResponseDto;
import com.spartanullnull.otil.domain.reportpost.entity.ReportPost;
import com.spartanullnull.otil.domain.reportpost.repository.ReportPostRepository;
import com.spartanullnull.otil.domain.user.entity.User;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ReportPostService {

    private final ReportPostRepository reportPostRepository;

    private final AmazonS3 amazonS3;

    @Value("&{cloud.aws.s3.bucket}")
    private String bucket;

    public ReportPostResponseDto createReport(ReportPostRequestDto requestDto, User user, MultipartFile imageFile) throws IOException {
        String imageUrl = saveFile(imageFile);

        ReportPost reportPost = new ReportPost(requestDto);
        reportPost.setUser(user);
        reportPost.setImageUrl(imageUrl);

        reportPostRepository.save(reportPost);

        return new ReportPostResponseDto(reportPost);
    }

    public String saveFile(MultipartFile multipartFile) throws IOException {
        String originalFilename = multipartFile.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        amazonS3.putObject(bucket, originalFilename, multipartFile.getInputStream(), metadata);

        return amazonS3.getUrl(bucket, originalFilename).toString();
    }

    @Transactional(readOnly = true)
    public Page<ReportPostResponseDto> getUserAllReportPost(User user, PageRequestDto page) {
        Pageable pageable = page.toPageable();

        Page<ReportPost> reportPosts = reportPostRepository.findByUser(user, pageable);

        return reportPosts.map(ReportPostResponseDto::new);
    }

    @Transactional
    public Page<ReportPostResponseDto> getAdminAllReportPost(PageRequestDto page) {
        Pageable pageable = page.toPageable();

        Page<ReportPost> reportPosts = reportPostRepository.findAll(pageable);

        if (reportPosts != null) {
            return reportPosts.map(ReportPostResponseDto::new);
        } else {
            throw new IllegalArgumentException("신고 게시글이 존재하지 않습니다");
        }
    }

    public ReportPostResponseDto getReport(User user, Long postid) {
        ReportPost reportPost = getByUserAndId(postid, user);
        if (reportPost != null) {
            return new ReportPostResponseDto(reportPost);
        } else {
            throw new IllegalArgumentException("신고 게시글이 존재하지 않습니다.");
        }

    }

    @Transactional
    public ReportPostResponseDto updateReport(Long postid, ReportPostRequestDto requestDto,
        User user) {
        ReportPost reportPost = getByUserAndId(postid, user);
        reportPost.setUser(user);
        reportPost.setName(requestDto.getName());
        reportPost.setTitle(requestDto.getTitle());
        reportPost.setContent(requestDto.getContent());

        if (reportPost != null) {
            reportPostRepository.save(reportPost);
            return new ReportPostResponseDto(reportPost);
        } else {
            throw new IllegalArgumentException("신고 게시글이 존재하지 않습니다");
        }
    }

    @Transactional
    public void deleteReport(Long postid, User user) {
        ReportPost byUserAndId = getByUserAndId(postid, user);
        if (byUserAndId != null) {
            reportPostRepository.deleteById(postid);
        }else{
            throw new IllegalArgumentException("신고 게시글이 존재하지 않습니다");
        }
    }

    private ReportPost getByUserAndId(Long postid, User user) {
        return reportPostRepository.findByUserAndId(user, postid);
    }
}
