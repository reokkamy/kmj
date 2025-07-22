package com.example.kmj.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardServiceTest {

    private final BoardService boardService = new BoardService();


    @Test
    public void 씨발_욕설_테스트() {
        String input = "씨발놈아";
        String expectedUserOutput = "**놈아";  // 씨발(2글자) -> **(2개 별표)
        String expectedAdminOutput = "씨발가 포함된 문장입니다";

        String userOutput = boardService.filterBadWordsForUser(input);
        String adminOutput = boardService.checkBadWordsForAdmin(input);

        System.out.println("입력: " + input);
        System.out.println("사용자 출력: " + userOutput);
        System.out.println("관리자 메시지: " + adminOutput);

        assertEquals(expectedUserOutput, userOutput);
        assertEquals(expectedAdminOutput, adminOutput);
    }

    @Test
    public void 관리자_원본텍스트_DB저장_테스트() {
        String input = "씨발놈아";  // 단일 비속어로 테스트

        // 사용자에게는 마스킹된 텍스트
        String userOutput = boardService.filterBadWordsForUser(input);
        System.out.println("사용자 출력: " + userOutput);

        // 관리자는 비속어 감지 메시지
        String adminMessage = boardService.checkBadWordsForAdmin(input);
        System.out.println("관리자 알림: " + adminMessage);

        // DB에는 원본 텍스트 저장
        String originalForDB = boardService.getOriginalTextForAdmin(input);
        System.out.println("DB 저장용 원본: " + originalForDB);

        assertEquals("**놈아", userOutput);
        assertEquals("씨발가 포함된 문장입니다", adminMessage);
        assertEquals(input, originalForDB); // 원본 그대로
        assertTrue(boardService.containsBadWords(input));
    }

    @Test
    public void 비속어가_없는_경우_테스트() {
        String input = "안녕하세요 좋은 하루입니다";
        String userResult = boardService.filterBadWordsForUser(input);
        String adminResult = boardService.checkBadWordsForAdmin(input);

        assertEquals(input, userResult); // 변화 없음
        assertNull(adminResult); // null 반환
        assertFalse(boardService.containsBadWords(input));
    }

    @Test
    public void 복합_비속어_테스트() {
        String input = "미친새끼 병신아";
        String userOutput = boardService.filterBadWordsForUser(input);
        String adminOutput = boardService.checkBadWordsForAdmin(input);

        System.out.println("입력: " + input);
        System.out.println("사용자 출력: " + userOutput);
        System.out.println("관리자 메시지: " + adminOutput);

        // 미친새끼(4글자) -> ****, 병신(2글자) -> **
        assertEquals("**** **아", userOutput);
        assertNotNull(adminOutput);
        assertTrue(boardService.containsBadWords(input));
    }
}