package com.example.kmj.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String content;
    
    private String writer;
    
    @Column(name = "is_blinded")
    private boolean isBlinded = false;
    
    @Column(name = "blind_reason")
    private String blindReason;
    
    @Column(name = "blind_date")
    private LocalDateTime blindDate;
    
    @Column(name = "original_content", columnDefinition = "TEXT")
    private String originalContent;
    
    @Column(name = "original_title")
    private String originalTitle;
    
    // 블라인드 처리 메서드
    public void blindPost(String reason) {
        if (!this.isBlinded) {
            this.originalTitle = this.title;
            this.originalContent = this.content;
            this.title = "[블라인드 처리된 게시글]";
            this.content = "신고가 누적되어 블라인드 처리되었습니다";
            this.isBlinded = true;
            this.blindReason = reason;
            this.blindDate = LocalDateTime.now();
        }
    }
    
    // 블라인드 해제 메서드
    public void unblindPost() {
        if (this.isBlinded && this.originalTitle != null && this.originalContent != null) {
            this.title = this.originalTitle;
            this.content = this.originalContent;
            this.isBlinded = false;
            this.blindReason = null;
            this.blindDate = null;
            this.originalTitle = null;
            this.originalContent = null;
        }
    }
}