package com.example.kmj.controller;

import com.example.kmj.entity.Report;
import com.example.kmj.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ReportController {

    private final ReportRepository reportRepository;

    @PostMapping("/report/submit")
    public String submitReport(@RequestParam Long boardId,
                               @RequestParam String reason,
                               Principal principal,
                               RedirectAttributes rttr) {

        String reporter = (principal != null) ? principal.getName() : "anonymous";

        boolean exists = reportRepository.findByBoardIdAndReporter(boardId, reporter).isPresent();
        if (exists) {
            rttr.addFlashAttribute("error", "이미 신고한 게시글입니다.");
            return "redirect:/board/list";
        }

        Report report = new Report();
        report.setBoardId(boardId);
        report.setReporter(reporter);
        report.setReason(reason);
        reportRepository.save(report);

        rttr.addFlashAttribute("success", "신고가 접수되었습니다.");
        return "redirect:/board/list";
    }
}
