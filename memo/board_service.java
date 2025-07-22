package com.example.kmj.service;

import com.example.kmj.entity.Board;
import com.example.kmj.repository.BoardRepository;
import com.example.kmj.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final ReportRepository reportRepository;
    
    private List<String> badWords;

    public BoardService(BoardRepository boardRepository, ReportRepository reportRepository) {
        this.boardRepository = boardRepository;
        this.reportRepository = reportRepository;
        loadBadWords();
    }

    // 신고 누적 확인 및 블라인드 처리
    @Transactional
    public void checkAndBlindPost(Long boardId) {
        // 해당 게시글의 신고 수 확인
        Long reportCount = reportRepository.countReportsByBoardId(boardId);
        
        if (reportCount >= 3) {
            Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
            
            if (!board.isBlinded()) {
                board.blindPost("신고 누적");
                boardRepository.save(board);
                System.out.println("게시글 ID " + boardId + "이(가) 신고 누적으로 블라인드 처리되었습니다.");
            }
        }
    }

    // 블라인드 해제
    @Transactional
    public void unblindPost(Long boardId) {
        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        
        board.unblindPost();
        boardRepository.save(board);
    }

    // 게시글 조회 (블라인드 상태 고려)
    public Board getBoard(Long boardId) {
        return boardRepository.findById(boardId)
            .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
    }

    // 비속어 필터링: 사용자용 (별표 처리)
    public String filterBadWordsForUser(String input) {
        if (badWords == null || badWords.isEmpty()) return input;

        String result = input;

        // 긴 비속어부터 처리 (부분 문자열 문제 방지)
        List<String> sortedBadWords = new ArrayList<>(badWords);
        sortedBadWords.sort(Comparator.comparingInt(String::length).reversed());

        for (String badWord : sortedBadWords) {
            if (result.contains(badWord)) {
                String stars = "*".repeat(badWord.length());
                result = result.replace(badWord, stars);
            }
        }
        return result;
    }

    // 비속어 포함 여부 확인 후 관리자용 메시지 반환 (원본 텍스트와 함께)
    public String checkBadWordsForAdmin(String input) {
        if (badWords == null || badWords.isEmpty()) return null;

        for (String badWord : badWords) {
            if (input.contains(badWord)) {
                return badWord + "가 포함된 문장입니다";
            }
        }
        return null;
    }

    // 관리자용: 원본 텍스트 반환 (DB 저장용)
    public String getOriginalTextForAdmin(String input) {
        return input; // 원본 그대로 반환
    }

    // 비속어가 포함되어 있는지 확인
    public boolean containsBadWords(String input) {
        if (badWords == null || badWords.isEmpty()) return false;

        for (String badWord : badWords) {
            if (input.contains(badWord)) {
                return true;
            }
        }
        return false;
    }

    // badwords.txt 파일에서 비속어 목록 로드
    private void loadBadWords() {
        badWords = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/static/badwords.txt"), "UTF-8"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String word = line.trim();
                if (!word.isEmpty()) {
                    badWords.add(word);
                }
            }
            System.out.println("비속어 목록 로드 완료: " + badWords.size() + "개");

        } catch (Exception e) {
            System.err.println("badwords.txt 파일을 읽을 수 없습니다: " + e.getMessage());
            // 테스트용 기본 비속어 추가
            badWords.add("개새끼");
            badWords.add("씨발");
            System.out.println("기본 비속어 목록을 사용합니다.");
        }
    }
}