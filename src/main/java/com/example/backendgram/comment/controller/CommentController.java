package com.example.backendgram.comment.controller;

import com.example.backendgram.CommonResponseDto;
import com.example.backendgram.comment.dto.CommentRequestDto;
import com.example.backendgram.comment.dto.CommentResponseDto;
import com.example.backendgram.comment.service.CommentService;
import com.example.backendgram.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.RejectedExecutionException;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        CommentResponseDto commentResponseDto = commentService.createComment(commentRequestDto,userDetails.getUser());
        return ResponseEntity.status(201).body(commentResponseDto);
    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<CommonResponseDto> patchComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            CommentResponseDto commentResponseDto = commentService.patchComment(commentId, commentRequestDto, userDetails.getUser());
            return ResponseEntity.ok().body(new CommonResponseDto("댓글 수정 성공",HttpStatus.OK.value()));
        } catch (RejectedExecutionException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommonResponseDto> deleteComment(@PathVariable Long commentId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            CommentResponseDto commentResponseDto = commentService.deleteService(commentId, commentRequestDto, userDetails.getUser());
            return ResponseEntity.ok().body(new CommonResponseDto("댓글 삭제 성공",HttpStatus.OK.value()));
        } catch (RejectedExecutionException | IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

}
