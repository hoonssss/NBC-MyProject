package com.example.backendgram.newsfeed.service;

import com.example.backendgram.exception.NewsfeedNotFoundException;
import com.example.backendgram.folder.entity.Folder;
import com.example.backendgram.folder.entity.NewsfeedFolder;
import com.example.backendgram.folder.repository.FolderRepository;
import com.example.backendgram.newsfeed.Entity.NewsFeed;
import com.example.backendgram.newsfeed.dto.NewsFeedRequestDto;
import com.example.backendgram.newsfeed.dto.NewsFeedResponseDto;
import com.example.backendgram.newsfeed.repository.NewsFeedRepository;
import com.example.backendgram.newsfeed.repository.NewsfeedFolderRepository;
import com.example.backendgram.user.entity.User;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.RejectedExecutionException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NewsFeedService {

    private final NewsFeedRepository newsFeedRepository;
    private final FolderRepository folderRepository;
    private final NewsfeedFolderRepository newsfeedFolderRepository;
    private final MessageSource messageSource;


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
        if(!newsFeed.getLikes().contains(user)){
//            newsFeed.getLikes().add(user);
            user.likeNewsfeed(newsFeed);
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

    public void addFolder(Long newsfeedId, Long folderId, User user) { //게시글 폴더 추가
        NewsFeed newsFeed = newsFeedRepository.findById(newsfeedId).orElseThrow(
                () -> new NullPointerException("해당 게시글이 존재하지 않습니다.")
        );
        Folder folder = folderRepository.findById(folderId).orElseThrow(
                () -> new NullPointerException("해당 폴더가 존재하지 않습니다.")
        );
        if(!newsFeed.getUser().getId().equals(user.getId()) || !folder.getUser().getId().equals(user.getId())){
            throw new IllegalArgumentException("회원님의 관싱상품이 아니거나, 회원님의 폴더가 아닙니다.");
        }
        Optional<NewsfeedFolder> overlapFolder = newsfeedFolderRepository.findByNewsFeedAndFolder(newsFeed, folder);
        if(overlapFolder.isPresent()){
            throw new NewsfeedNotFoundException(messageSource.getMessage("not.found.newsfeed",null,"Not Found Newsfeed",
                Locale.getDefault()));
        }
        newsfeedFolderRepository.save(new NewsfeedFolder(newsFeed,folder));
    }

    public Page<NewsFeedResponseDto> getNewsfeedsInFolder(Long folderId, int page, int size, String sortBy, boolean isAsc, User user) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        //현재 로그인한 user가 등록한 특정 폴더에 속해있는 newsfeed_id를 기준으로 newsfeed를 가져옴
        Page<NewsFeed> NewsfeedList = newsFeedRepository.findAllByUserAndNewsfeedFolders_FolderId(
            user, folderId, pageable);

        Page<NewsFeedResponseDto> responseDtoList = NewsfeedList.map(NewsFeedResponseDto::new);
        return responseDtoList;
    }
}
