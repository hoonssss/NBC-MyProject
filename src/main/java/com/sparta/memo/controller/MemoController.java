package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.service.MemoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MemoController {

//    private final JdbcTemplate jdbcTemplate;
//
//    public MemoController(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }

    //MemoController -> MemoService -> MemoRepository(강한결합)
    private final MemoService memoService;

//    public MemoController(JdbcTemplate jdbcTemplate){
//        this.memoService = new MemoService(jdbcTemplate);
//    }

    public MemoController(MemoService memoService){
        this.memoService = memoService;
    }

    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {
//        MemoService memoService = new MemoService(jdbcTemplate);
        return memoService.createMethod(requestDto);

    }

    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() {
//        MemoService memoService = new MemoService(jdbcTemplate);
        return memoService.getMethod();
    }

    @GetMapping("memos/contents")
    public List<MemoResponseDto> getKeyword(String keyword){
        return memoService.getKeyWord(keyword);
    }

    @PutMapping("/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto) {
        // 해당 메모가 DB에 존재하는지 확인
//        MemoService memoService = new MemoService(jdbcTemplate);
        return memoService.putMethod(id, requestDto);

    }

    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id) {
//        MemoService memoService = new MemoService(jdbcTemplate);
        return memoService.deleteMethod(id);
    }
}