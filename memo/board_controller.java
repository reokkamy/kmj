package com.example.kmj.controller;

import com.example.kmj.entity.Board;
import com.example.kmj.repository.BoardRepository;
import com.example.kmj.repository.ReportRepository;
import com.example.kmj.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardRepository boardRepository;
    private final ReportRepository reportRepository;
    private final BoardService boardService;

    @GetMapping("/board/list")
    public String listPage(Model model) {
        // 모든 게시글 조회
        List<Board> boards = boardRepository.findAll();
        
        // 각 게시글의 신고 수 정보도 함께 전달
        for (Board board : boards) {
            Long reportCount = reportRepository.countReportsByBoardId(board.getId());
            // 필요시 reportCount를 Board 객체나 별도 DTO에 추가할 수 있음
        }
        
        model.addAttribute("boards", boards);
        return "board/list";
    }

    @GetMapping("/board/detail/{id}")
    public String detailPage(@PathVariable Long id, Model model) {
        Board board = boardService.getBoard(id);
        Long reportCount = reportRepository.countReportsByBoardId(id);
        
        model.addAttribute("board", board);
        model.addAttribute("reportCount", reportCount);
        
        return "board/detail";
    }

    // 테스트용 게시글 생성 (개발 시에만 사용)
    @GetMapping("/board/create-test")
    public String createTestBoard() {
        Board board = new Board();
        board.setTitle("테스트 게시글");
        board.setContent("이것은 신고 테스트용 게시글입니다.");
        board.setWriter("testUser");
        
        boardRepository.save(board);
        
        return "redirect:/board/list";
    }
}