package com.sparta.memo.service;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import com.sparta.memo.repository.MemoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MemoService {

//    private final JdbcTemplate jdbcTemplate;
//
//    public MemoService(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
    private final MemoRepository memoRepository;
//    @Autowired
//    public void setMemoService(MemoRepository memoService){
//        this.memoRepository = memoService;
//    }

    private MemoService(MemoRepository memoRepository){
        this.memoRepository = memoRepository;
    }

//    public MemoService(JdbcTemplate jdbcTemplate){
//        this.memoRepository = new MemoRepository(jdbcTemplate);
//    }



    public MemoResponseDto createMethod(MemoRequestDto requestDto) {
        // RequestDto -> Entity
        Memo memo = new Memo(requestDto);

//        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);
        Memo saveMemo = memoRepository.save(memo);

        // Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;
    }

    public List<MemoResponseDto> getMethod() {
//        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);
        return memoRepository.findAll();
    }

    public Long putMethod(Long id, MemoRequestDto requestDto) {
//        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);

        Memo memo = memoRepository.findById(id);
        if(memo != null) {
            memoRepository.update(id, requestDto);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    public Long deleteMethod(Long id) {
        // 해당 메모가 DB에 존재하는지 확인
//        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);
        Memo memo = memoRepository.findById(id);
        if(memo != null) {
            memoRepository.delete(id);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }
}
