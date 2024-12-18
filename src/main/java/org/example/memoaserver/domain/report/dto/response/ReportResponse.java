package org.example.memoaserver.domain.report.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.example.memoaserver.domain.post.entity.PostEntity;
import org.example.memoaserver.domain.report.entity.ReportEntity;
import org.example.memoaserver.domain.report.entity.enums.ReportStatus;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ReportResponse {

    private ReportEntity reportId;

    private PostEntity postId;

    private UserEntity userId;

    private ReportStatus status;

    private String reason;

    public static ReportResponse fromReportEntity(ReportEntity reportEntity) {
        return ReportResponse.builder()
                .postId(reportEntity.getPost())
                .userId(reportEntity.getUser())
                .status(reportEntity.getStatus())
                .reason(reportEntity.getReason())
                .build();
    }
}