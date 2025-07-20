package com.example.kmj.repository;

import com.example.kmj.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<Report> findByBoardIdAndReporter(Long boardId, String reporter);
}
