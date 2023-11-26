package com.example.backendgram.newsfeed.service;

import com.example.backendgram.newsfeed.Entity.NewsFeed;
import com.example.backendgram.newsfeed.dto.NewsFeedRequestDto;
import com.example.backendgram.newsfeed.dto.NewsFeedResponseDto;
import com.example.backendgram.newsfeed.repository.NewsFeedRepository;
import com.example.backendgram.security.UserDetailsImpl;
import com.example.backendgram.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsFeedService {

    NewsFeedRepository newsFeedRepository;

    public NewsFeedResponseDto createNewsFeed(NewsFeedRequestDto newsFeedRequestDto, User user) {
        NewsFeed newsFeed = new NewsFeed(newsFeedRequestDto);
        newsFeed.setUser(user);

        newsFeedRepository.save(newsFeed);

        return new NewsFeedResponseDto(newsFeed);
    }

    //JH
    public List<NewsFeedResponseDto> getAllNewsFeed() {
        List<NewsFeed> newsFeeds = newsFeedRepository.findAll();
        return newsFeeds.stream().map(
                NewsFeedResponseDto::new
        ).collect(Collectors.toList());
    }

    public NewsFeedResponseDto getNewsFeed(Long id) {
        NewsFeed newsFeed = getFeed(id);
        return new NewsFeedResponseDto(newsFeed);
    }

    public NewsFeedResponseDto patchNewsFeed(Long id, NewsFeedRequestDto requestDto, User user) {
        NewsFeed newsFeed = getUser(id,user);

        newsFeed.setTitle(requestDto.getTitle());
        newsFeed.setContent(requestDto.getContent());

        return new NewsFeedResponseDto(newsFeed);
    }

    public NewsFeedResponseDto deleteFeed(Long id, User user) {
        NewsFeed newsFeed = getUser(id,user);

        newsFeedRepository.delete(newsFeed);

        return new NewsFeedResponseDto(newsFeed);
    }

    public NewsFeedResponseDto likeFeed(Long id, User user) {
        NewsFeed newsFeed = getFeed(id);

        newsFeed.getLikes().add(user);
        newsFeedRepository.save(newsFeed);
        return convertToDto(newsFeed);
    }

    public NewsFeedResponseDto unlikeFeed(Long id, User user) {
        NewsFeed newsFeed = getFeed(id);

        newsFeed.getLikes().remove(user);
        newsFeedRepository.save(newsFeed);

        return convertToDto(newsFeed);
    }

    public NewsFeedResponseDto convertToDto(NewsFeed newsFeed){
        NewsFeedResponseDto responseDTO = new NewsFeedResponseDto();

        newsFeed.setId(newsFeed.getId());
        newsFeed.setContent(newsFeed.getContent());

        responseDTO.setLikeCount(newsFeed.getLikes().size());
        return responseDTO;
    }

    public NewsFeed getFeed(Long id) {
        return newsFeedRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않습니다.")
        );
    }

    public NewsFeed getUser(Long id, User user){
        NewsFeed newsFeed = getFeed(id);

        if(!user.getId().equals(id)){
            throw new RejectedExecutionException("작성자만 수정,삭제 할 수 있습니다.");
        }
        return newsFeed;
    }


}
