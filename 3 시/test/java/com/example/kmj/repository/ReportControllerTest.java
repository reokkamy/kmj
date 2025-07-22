package com.example.kmj.repository;

import com.example.kmj.entity.Report;
import com.example.kmj.repository.ReportRepository;
import com.example.kmj.controller.ReportController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ReportControllerTest {

    private ReportRepository reportRepository;
    private ReportController controller;

    @BeforeEach
    void setup() {
        reportRepository = Mockito.mock(ReportRepository.class);
        controller = new ReportController(reportRepository);
    }

    @Test
    void 신고_중복이면_에러리턴() {
        Long boardId = 1L;
        String reason = "불쾌한 게시물";
        String reporter = "testUser";

        // 이미 신고한 것으로 설정
        Mockito.when(reportRepository.findByBoardIdAndReporter(boardId, reporter))
                .thenReturn(Optional.of(new Report()));

        // 가짜 Principal & Redirect
        Principal principal = () -> reporter;
        RedirectAttributes redirectAttributes = Mockito.mock(RedirectAttributes.class);

        String view = controller.submitReport(boardId, reason, principal, redirectAttributes);
        assertEquals("redirect:/board/list", view);
    }
}
