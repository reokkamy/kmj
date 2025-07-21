package com.example.kmj.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {

    private List<String> badWords;

    public BoardService() {
        loadBadWords();
    }

    // 비속어 필터링: 사용자용 (별표 처리)
    public String filterBadWordsForUser(String input) {
        if (badWords == null || badWords.isEmpty()) return input;

        String result = input;
        for (String bad : badWords) {
            if (result.contains(bad)) {
                String stars = "*".repeat(bad.length());
                result = result.replace(bad, stars); // 포함되기만 하면 치환
            }
        }
        return result;
    }

    // 비속어 포함 여부 확인 후 관리자용 메시지 반환
    public String checkBadWordsForAdmin(String input) {
        if (badWords == null || badWords.isEmpty()) return null;

        for (String bad : badWords) {
            if (input.contains(bad)) {
                return bad + "가 포함된 문장입니다";
            }
        }
        return null;
    }

    // badwords.txt 로드
    private void loadBadWords() {
        badWords = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream("/static/badwords.txt")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                badWords.add(line.trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
