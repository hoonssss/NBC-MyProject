package com.sparta.jhboardapp.service;

import com.sparta.jhboardapp.controller.AuthorizException;
import com.sparta.jhboardapp.dto.AddRequestDto;
import com.sparta.jhboardapp.dto.ResponseDto;
import com.sparta.jhboardapp.dto.UpdateRequestDto;
import com.sparta.jhboardapp.entity.BoardEntity;
import com.sparta.jhboardapp.repository.BoardAppRepository;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardAppService {

    @Autowired
    BoardAppRepository boardAppRepository;

    public ResponseDto addPost(AddRequestDto addRequestDto) {
        BoardEntity boardEntity = new BoardEntity(addRequestDto);
        BoardEntity save = boardAppRepository.save(boardEntity);
        return new ResponseDto(save);
    }

    public List<ResponseDto> getPosts() {
        return boardAppRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(
                        ResponseDto::new
                ).collect(Collectors.toList());
    }

    public ResponseDto getPost(Long id){
        BoardEntity boardEntity = getBoard(id);
        return new ResponseDto(boardEntity);
    }

    @Transactional
    public ResponseDto pathchPost(Long id, UpdateRequestDto requestDto){
        BoardEntity boardEntity = getBoard(id);
        extracted(requestDto.getPassword(), boardEntity);
        boardEntity.update(requestDto);
        return new ResponseDto(boardEntity);
    }

    public void deletePost(Long id, String password){
        BoardEntity boardEntity = getBoard(id);
        extracted(password, boardEntity);
    }

    private static void extracted(String password, BoardEntity boardEntity){
        if(!boardEntity.getPassword().equals(password)){
            throw  new AuthorizException("비밀번호 오류");
        }
    }

    private BoardEntity getBoard(Long id) {
        return boardAppRepository.findById(id).orElseThrow(
                () -> new NullPointerException("오류 발생")
        );
    }
}
