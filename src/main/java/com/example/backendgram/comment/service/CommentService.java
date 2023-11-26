package com.example.backendgram.comment.service;

import com.example.backendgram.comment.dto.CommentRequestDto;
import com.example.backendgram.comment.dto.CommentResponseDto;
import com.example.backendgram.comment.entity.Comment;
import com.example.backendgram.comment.repository.CommentRepository;
import com.example.backendgram.newsfeed.Entity.NewsFeed;
import com.example.backendgram.newsfeed.repository.NewsFeedRepository;
import com.example.backendgram.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final NewsFeedRepository newsFeedRepository;

    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, User user) {
        NewsFeed newsFeed = newsFeedRepository.findById(commentRequestDto.getNewsFeedId()).orElseThrow(
                () -> new UsernameNotFoundException("오류 발생")
        );

        Comment comment = new Comment(commentRequestDto);
        comment.setUser(user);
        comment.setNewsFeed(newsFeed);

        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    public CommentResponseDto patchComment(Long commentId, CommentRequestDto commentRequestDto, User user) {
        Comment comment = getComment(commentId, commentRequestDto, user);

        comment.setText(commentRequestDto.getText());
        comment.setPassword(commentRequestDto.getPassword());
        return new CommentResponseDto(comment);
    }

    public CommentResponseDto deleteService(Long commentId, CommentRequestDto commentRequestDto, User user) {
        Comment comment = getComment(commentId, commentRequestDto, user);

        commentRepository.delete(comment);
        return new CommentResponseDto(comment);
    }

    private Comment getComment(Long commentId, CommentRequestDto commentRequestDto, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new UsernameNotFoundException("존재하지 않습니다.")
        );
        if(!user.getId().equals(comment.getUser().getId()) && !user.getPassword().equals(commentRequestDto.getPassword())){
            throw new RejectedExecutionException("작성자만 접근 가능합니다.");
        }
        return comment;
    }
}
