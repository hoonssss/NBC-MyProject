package com.sparta.jhboardapp.controller;

import com.sparta.jhboardapp.dto.AddRequestDto;
import com.sparta.jhboardapp.dto.ErrorResponseDto;
import com.sparta.jhboardapp.dto.ResponseDto;
import com.sparta.jhboardapp.dto.UpdateRequestDto;
import com.sparta.jhboardapp.service.BoardAppService;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/posts")
@RestController
@RequiredArgsConstructor
public class BoardAppController {

    private final BoardAppService boardAppService;

    @PostMapping
    public ResponseEntity<ResponseDto> addPost(@RequestBody AddRequestDto addRequestDto){
        ResponseDto responseDto = boardAppService.addPost(addRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<ResponseDto>> getPosts(){
        List<ResponseDto> responseDtos = boardAppService.getPosts();
        return ResponseEntity.ok(responseDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getPost(@PathVariable Long id){
        ResponseDto responseDto = boardAppService.getPost(id);
        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResponseDto> patchPost(@PathVariable Long id, @RequestBody UpdateRequestDto requestDto){
        ResponseDto responseDto = boardAppService.pathchPost(id, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, @RequestHeader("password") String password){
        boardAppService.deletePost(id,password);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> boardNotFoundEx(BoardNotFoundException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponseDto(
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage()
                )
        );
    }//NotFound

    @ExceptionHandler(AuthorizException.class)
    public ResponseEntity<ErrorResponseDto> AuthException(AuthorizException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ErrorResponseDto(
                        HttpStatus.UNAUTHORIZED.value(), ex.getMessage()
                )
        );
    }//AuthException
}
