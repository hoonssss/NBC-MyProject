package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MemoController {

    Map<Long,Memo> memoMap = new HashMap<>();
    @PostMapping("/memos")
    public MemoResponseDto responseDto(@RequestBody MemoRequestDto requestDto){
        Memo memo = new Memo(requestDto);
        Long maxId = memoMap.size() > 0 ? Collections.max(memoMap.keySet()) + 1 : 1 ;
        memo.setId(maxId);

        memoMap.put(memo.getId(),memo);

        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;
    }

    @GetMapping("/memos")
    public List<MemoResponseDto> memoResponseDtos(){
        List<MemoResponseDto> memoResponseDtos = memoMap.values()
                .stream()
                .map(MemoResponseDto::new)
                .toList();

        return memoResponseDtos;
    }

    @PutMapping("/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto){
        //해당 메모가 DB에 존재하는지 확인
        if(memoMap.containsKey(id)){ //boolean type
            Memo memo = memoMap.get(id);
            // memo 수정
            memo.update(requestDto);
            return memo.getId();
        }else{
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id) {
        if (memoMap.containsKey(id)) {
            memoMap.remove(id);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }
}

