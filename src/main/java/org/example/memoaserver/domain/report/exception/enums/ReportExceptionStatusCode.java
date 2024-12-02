package org.example.memoaserver.domain.report.exception.enums;

import lombok.AllArgsConstructor;
import org.example.memoaserver.global.exception.enums.StatusCode;

@AllArgsConstructor
public enum ReportExceptionStatusCode implements StatusCode {
    REPORT_NOT_FOUND(404, "해당 게시물에 대한 신고 내역이 없음");

    private final int status;
    private final String message;

    @Override
    public int getStatusCode() {
        return this.status;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String getExceptionName() {
        return this.name();
    }
}