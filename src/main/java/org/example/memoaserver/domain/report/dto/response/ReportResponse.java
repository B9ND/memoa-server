package org.example.memoaserver.domain.report.dto.response;

import org.example.memoaserver.domain.post.entity.PostEntity;
import org.example.memoaserver.domain.report.entity.enums.ReportStatus;
import org.example.memoaserver.domain.user.entity.UserEntity;

public class ReportResponse {
    private Long reportId;
    private PostEntity postId;
    private UserEntity userId;
    private ReportStatus status;
    private String reason;
}
