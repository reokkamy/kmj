package com.example.kmj.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"boardId", "reporter"})})
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long boardId;
    private String reporter;
    @Column(columnDefinition = "TEXT")
    private String reason;
    private LocalDateTime reportDate = LocalDateTime.now();
}
