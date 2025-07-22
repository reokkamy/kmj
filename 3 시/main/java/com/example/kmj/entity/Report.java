package com.example.kmj.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "board_id", nullable = false)
    private Long boardId;

    @Column(name = "reporter", nullable = false)
    private String reporter;

    @Column(name = "reason", nullable = false, columnDefinition = "TEXT")
    private String reason;

    @Column(name = "report_date", nullable = false)
    private LocalDateTime reportDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ReportStatus status = ReportStatus.PENDING;

    @PrePersist
    protected void onCreate() {
        this.reportDate = LocalDateTime.now();
    }

    public enum ReportStatus {
        PENDING,    // 대기중
        REVIEWED,   // 검토완료
        RESOLVED    // 처리완료
    }
}