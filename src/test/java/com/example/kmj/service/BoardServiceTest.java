package com.example.kmj.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardServiceTest {

    private final BoardService boardService = new BoardService(null);

    @Test
    void 비속어가_치환되는지_테스트() {
        String 입력값 = "이건 씨발이 포함된 문장입니다.";
        String 결과 = boardService.filterBadWords(입력값);
        System.out.println("결과: " + 결과);

        assertFalse(결과.contains("씨발"));
        assertTrue(결과.contains("**"));
    }
}
