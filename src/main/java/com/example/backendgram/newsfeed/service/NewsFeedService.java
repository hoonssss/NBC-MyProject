package com.example.backendgram.newsfeed.service;

import com.example.backendgram.newsfeed.Entity.NewsFeed;
import com.example.backendgram.newsfeed.dto.NewsFeedRequestDto;
import com.example.backendgram.newsfeed.dto.NewsFeedResponseDto;
import com.example.backendgram.newsfeed.repository.NewsFeedRepository;
import com.example.backendgram.security.UserDetailsImpl;
import com.example.backendgram.user.entity.User;
import com.example.backendgram.user.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsFeedService {

    @Autowired
    NewsFeedRepository newsFeedRepository;

    public NewsFeedResponseDto createNewsFeed(NewsFeedRequestDto newsFeedRequestDto, User user) {
        NewsFeed newsFeed = new NewsFeed(newsFeedRequestDto);
        newsFeed.setUser(user);

        newsFeedRepository.save(newsFeed);

        return new NewsFeedResponseDto(newsFeed);
    }

//    public List<NewsFeedResponseDto> getAllNewsFeed() {
//        List<NewsFeed> newsFeeds = newsFeedRepository.findAll();
//        return newsFeeds.stream().map(
//                NewsFeedResponseDto::new
//        ).collect(Collectors.toList());
//    }

    @Transactional(readOnly = true)
    public Page<NewsFeedResponseDto> getAllNewsFeeds(User user, int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction,sortBy);
        Pageable pageable = PageRequest.of(page,size,sort);

        UserRoleEnum userRoleEnum = user.getRole();

        Page<NewsFeed> newsFeeds;

        newsFeeds = newsFeedRepository.findAll(pageable);

        return newsFeeds.map(NewsFeedResponseDto::new);

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

    @Transactional
    public NewsFeedResponseDto likeFeed(Long id, User user) {
        NewsFeed newsFeed = getFeed(id);
        if(newsFeed.getLikes().contains(user)){
            newsFeed.getLikes().add(user);
            newsFeedRepository.save(newsFeed);
            return convertToDto(newsFeed);
        }else{
            throw new RejectedExecutionException("좋아요 오류 발생");
        }
    }

    @Transactional
    public NewsFeedResponseDto unlikeFeed(Long id, User user) {
        NewsFeed newsFeed = getFeed(id);
        if(newsFeed.getLikes().contains(user)){
            user.unlikeNewsfeed(newsFeed);
            newsFeedRepository.save(newsFeed);
            return convertToDto(newsFeed);
        }else{
            throw new RejectedExecutionException("좋아요 취소 오류 발생");
        }
    }

    public NewsFeedResponseDto convertToDto(NewsFeed newsFeed) {
        NewsFeedResponseDto responseDTO = new NewsFeedResponseDto();

        responseDTO.setId(newsFeed.getId());
        responseDTO.setContent(newsFeed.getContent());
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
