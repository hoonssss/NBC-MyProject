package com.sparta.spartaspringmemo.spartaMemo.Service;

import com.sparta.spartaspringmemo.Repository.MemoRepository;
import com.sparta.spartaspringmemo.spartaMemo.Memo;
import com.sparta.spartaspringmemo.spartaMemo.MemoRequestDto;
import com.sparta.spartaspringmemo.spartaMemo.MemoResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MemoService {

    MemoRepository memoRepository;
    public MemoService(MemoRepository memoRepository){
        this.memoRepository = memoRepository;
    }
    private Map<String, Memo> memoMap = new ConcurrentHashMap<>();

    public MemoResponseDto createMemo(MemoRequestDto requestDto) {
        Memo memo = new Memo(requestDto.getUsername(), requestDto.getTitle(), requestDto.getPassword(), requestDto.getContents(), LocalDateTime.now());
        memoMap.put(memo.getUsername(), memo);
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);
        return memoResponseDto;
    }

    public List<MemoResponseDto> getMemo() {
        List<MemoResponseDto> responseDtos = new ArrayList<>();
        for (Memo memo : memoMap.values()) {
            responseDtos.add(new MemoResponseDto(memo.getTitle(), memo.getUsername(), memo.getContents(), memo.getDate()));
        }
        return responseDtos;
//        List<MemoResponseDto> memoResponseDtoList = memoMap.values()
//                .stream()
//                .map(MemoResponseDto::new)
//                .toList();
//        return memoResponseDtoList;

    }

    public MemoResponseDto getsMemo(String username) {
        Memo memo = memoMap.get(username);
        if (memo != null) {
            return new MemoResponseDto(memo.getTitle(), memo.getUsername(), memo.getContents(), memo.getDate());
        } else {
            throw new IllegalArgumentException("/get/{username}");
        }
    }

    public MemoResponseDto putMemo(String username, MemoRequestDto memoRequestDto) {
        Memo memo = memoMap.get(username);
        if (memo != null && memo.getPassword().equals(memoRequestDto.getPassword()) && memo.getUsername().equals(memoRequestDto.getUsername())) {
            memo.update(memoRequestDto);
            return new MemoResponseDto(memo.getTitle(), memo.getUsername(), memo.getContents(), memo.getDate());
        } else {
            throw new IllegalArgumentException("/put/{username}");
        }
    }

    public String deleteMemo(String username, String password) {
        Memo memo = memoMap.get(username);
        if (memo != null && memo.getPassword().equals(password)) {
            memoMap.remove(username, memo);
            return "삭제 완료";
        } else {
            throw new IllegalArgumentException("/delete/{username}");
        }
    }
}
