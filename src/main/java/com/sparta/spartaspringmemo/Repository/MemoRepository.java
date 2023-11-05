package com.sparta.spartaspringmemo.Repository;

import com.sparta.spartaspringmemo.spartaMemo.Memo;
import com.sparta.spartaspringmemo.spartaMemo.MemoResponseDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoRepository {
    private Map<String, Memo> memoMap = new ConcurrentHashMap<>();

    public Memo saveMemo(Memo memo) {

        return memo;
    }
}
