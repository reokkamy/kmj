package com.example.kmj.controller;


import com.example.kmj.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
@RequiredArgsConstructor
public class BoardController {

    @GetMapping("/board/list")
    public String listPage() {
        return "board/list";
    }
}