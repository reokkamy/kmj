package com.example.kmj.controller;


import com.example.kmj.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
@RequiredArgsConstructor
public class BoardController {

    /**
     * 게시글 목록 페이지를 반환합니다.
     * @return 게시글 목록 뷰 이름
     */
    @GetMapping("/board/list")
    public String listPage() {
        return "board/list";
    }
}