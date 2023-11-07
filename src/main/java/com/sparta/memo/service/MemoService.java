package com.sparta.memo.service;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import com.sparta.memo.repository.MemoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public MemoService(MemoRepository memoRepository){
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
        MemoResponseDto memoResponseDto = new MemoResponseDto(saveMemo);

        return memoResponseDto;
    }

    public List<MemoResponseDto> getMethod() {
//        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);
        return memoRepository.findAllByOrderByModifiedAtDesc()
                .stream()
                .map(MemoResponseDto::new)
                .toList();
//        return memoRepository.findAll()
//                .stream()
//                .map(MemoResponseDto::new)
//                .toList(); //stream에서 memo가 하나씩 빠져나가고 mpa에 의해서 변환이 될건데 MemoResponseDto에 생성자 중에
//                            // memo를 파라미터로 가지고 있는 생성자가 호출이 되고 그게 하나씩 변환이 되면서 그 값들을 List로 바꿔준다
    }

    @Transactional //변경감지를 위해 달아줘야함
    public Long putMethod(Long id, MemoRequestDto requestDto) {
//        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);
//        해당 id조회
//        Optional<Memo> memo = memoRepository.findById(id);
        Memo memo = memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("메모가없음") //null값일때
        );
        memo.update(requestDto);

        return id;
    }

    public Long deleteMethod(Long id) {
        // 해당 메모가 DB에 존재하는지 확인
//        MemoRepository memoRepository = new MemoRepository(jdbcTemplate);
//        Memo memo = memoRepository.findById(id);
//        if(memo != null) {
//            memoRepository.delete(id);
//            return id;
//        } else {
//            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
//        }
        Memo memo = findMemo(id);
        memoRepository.delete(memo);
        return id;
    }

    private Memo findMemo(Long id){
        return  memoRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("메모가없음") //null값일때
        );
    }
}
