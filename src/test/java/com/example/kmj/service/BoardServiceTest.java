package com.example.kmj.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardServiceTest {

    private BoardService boardService;

    @BeforeEach
    public void setUp() {
        boardService = new BoardService();
    }

    @Test
    public void 사용자출력_테스트() {
        String input = "넌 진짜 개새끼야";
        String expected = "넌 진짜 ***야";
        String result = boardService.filterBadWordsForUser(input);
        assertEquals(expected, result);
    }

    @Test
    public void 관리자출력_테스트() {
        String input = "넌 진짜 개새끼야";
        String expected = "개새끼가 포함된 문장입니다";
        String result = boardService.checkBadWordsForAdmin(input);
        assertEquals(expected, result);
    }
