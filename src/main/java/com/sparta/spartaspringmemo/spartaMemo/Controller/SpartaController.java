package com.sparta.spartaspringmemo.spartaMemo.Controller;

import com.sparta.spartaspringmemo.spartaMemo.MemoRequestDto;
import com.sparta.spartaspringmemo.spartaMemo.MemoResponseDto;
import com.sparta.spartaspringmemo.spartaMemo.Service.MemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SpartaController {

    private MemoService memoService;

    public SpartaController(MemoService memoService) {
        this.memoService = memoService;
    }

    @PostMapping("/post")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {
        return memoService.createMemo(requestDto);
    }

    @GetMapping("/get")
    public List<MemoResponseDto> getMemo() {
        return memoService.getMemo();
    }

    @GetMapping("/get/{username}")
    public MemoResponseDto getsMemo(@PathVariable String username) {
        return memoService.getsMemo(username);
    }

    @PutMapping("/put/{username}")
    public MemoResponseDto putMemo(@PathVariable String username, @RequestBody MemoRequestDto memoRequestDto) {
        return memoService.putMemo(username, memoRequestDto);
    }

    @DeleteMapping("/delete/{username}")
    public String deleteMemo(@PathVariable String username, @RequestParam String password) {
        return memoService.deleteMemo(username, password);
    }
}
