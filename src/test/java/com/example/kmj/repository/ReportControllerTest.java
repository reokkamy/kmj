package com.example.kmj.repository;

import com.example.kmj.controller.ReportController;
import com.example.kmj.dto.ReportRequestDTO;
import com.example.kmj.entity.Report;
import com.example.kmj.repository.ReportRepository;
import com.example.kmj.service.BoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReportControllerTest {

    private ReportRepository reportRepository;
    private BoardService boardService;
    private ReportController controller;

    @BeforeEach
    void setup() {
        reportRepository = Mockito.mock(ReportRepository.class);
        boardService = Mockito.mock(BoardService.class);
        controller = new ReportController(reportRepository, boardService);
    }

    @Test
    void 신고_중복이면_에러리턴() {
        // given
        Long boardId = 1L;
        String reason = "비속어 포함";
        String reporter = "testUser";

        ReportRequestDTO dto = new ReportRequestDTO();
        dto.setBoardId(boardId);
        dto.setReason(reason);

        Principal principal = () -> reporter;
        RedirectAttributes redirectAttributes = Mockito.mock(RedirectAttributes.class);

        // 이미 신고된 경우 설정
        Mockito.when(reportRepository.findByBoardIdAndReporter(boardId, reporter))
                .thenReturn(Optional.of(new Report()));

        // when
        String view = controller.submitReport(dto, principal, redirectAttributes);

        // then
        assertEquals("redirect:/board/list", view);
    }
}
