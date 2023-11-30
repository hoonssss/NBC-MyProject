package com.example.backendgram.newsfeed.controller;

import com.example.backendgram.CommonResponseDto;
import com.example.backendgram.newsFeedImage.dto.NewsFeedImageRequestDto;
import com.example.backendgram.newsFeedImage.service.NewsFeedImageService;
import com.example.backendgram.newsfeed.dto.NewsFeedRequestDto;
import com.example.backendgram.newsfeed.dto.NewsFeedResponseDto;
import com.example.backendgram.newsfeed.service.NewsFeedService;
import com.example.backendgram.security.Impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NewsFeedController {

    private final NewsFeedService newsFeedService;
    private final NewsFeedImageService newsFeedImageService;

    @PostMapping("user/post")
    public ResponseEntity<NewsFeedResponseDto> createNewsFeed(@RequestBody NewsFeedRequestDto newsFeedRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        NewsFeedResponseDto newsFeedResponseDto = newsFeedService.createNewsFeed(newsFeedRequestDto, userDetails.getUser());
        return ResponseEntity.status(201).body(newsFeedResponseDto);
    }

//    @GetMapping("user/gets")
//    public ResponseEntity<List<NewsFeedResponseDto>> getAllNewsFeeds() {
//        try {
//            List<NewsFeedResponseDto> responseDTO = newsFeedService.getAllNewsFeed();
//            return ResponseEntity.ok().body(responseDTO);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    @GetMapping("user/gets")
    public Page<NewsFeedResponseDto> getAllNewsFeeds(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc,
            @AuthenticationPrincipal UserDetailsImpl user) {
        return newsFeedService.getAllNewsFeeds(user.getUser(),page-1,size,sortBy,isAsc);
    }


    @GetMapping("user/{id}")
    public ResponseEntity<CommonResponseDto> getNewsFeed(@PathVariable Long id) {
        try {
            NewsFeedResponseDto newsFeedResponseDto = newsFeedService.getNewsFeed(id);
            return ResponseEntity.ok().body(newsFeedResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PatchMapping("user/{id}")
    public ResponseEntity<CommonResponseDto> patchNewsFeed(@PathVariable Long id,@RequestBody NewsFeedRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            NewsFeedResponseDto newsFeedResponseDto = newsFeedService.patchNewsFeed(id,requestDto,userDetails.getUser());
            return ResponseEntity.ok().body(newsFeedResponseDto);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }

    @DeleteMapping("user/{id}")
    public ResponseEntity<CommonResponseDto> deleteFeed(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            NewsFeedResponseDto newsFeedResponseDto = newsFeedService.deleteFeed(id, userDetails.getUser());
            return ResponseEntity.ok().body(newsFeedResponseDto);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(),HttpStatus.BAD_REQUEST.value()));
        }
    }

    @PostMapping("{id}/like")
    public ResponseEntity<NewsFeedResponseDto> likeFeed(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        NewsFeedResponseDto newsFeedResponseDto = newsFeedService.likeFeed(id,userDetails.getUser());
        return ResponseEntity.ok().body(newsFeedResponseDto);
    }

    @PostMapping("{id}/unlike")
    public ResponseEntity<NewsFeedResponseDto> unlikeFeed(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        NewsFeedResponseDto newsFeedResponseDto = newsFeedService.unlikeFeed(id,userDetails.getUser());
        return ResponseEntity.ok().body(newsFeedResponseDto);
    }

    @PostMapping("/image")
    public ResponseEntity<CommonResponseDto> setImage(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam("image") MultipartFile image) {
        try {
            newsFeedImageService.setImage(userDetails,image);
            return ResponseEntity.ok().body(new CommonResponseDto("Image set successfully", HttpStatus.OK.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }


}
