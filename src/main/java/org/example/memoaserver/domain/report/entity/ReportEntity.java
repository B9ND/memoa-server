package org.example.memoaserver.domain.report.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.memoaserver.domain.post.entity.PostEntity;
import org.example.memoaserver.domain.report.entity.enums.ReportStatus;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class ReportEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String reason;

    @Enumerated(EnumType.STRING)
    private ReportStatus staotus;

    @CreatedDate
    private LocalDateTime createdAt;

    private ReportStatus status;

    @Builder
    public ReportEntity(Long id, PostEntity post, UserEntity user, String reason, ReportStatus status) {
        this.id = id;
        this.post = post;
        this.user = user;
        this.reason = reason;
        this.status = status;
    }
}
