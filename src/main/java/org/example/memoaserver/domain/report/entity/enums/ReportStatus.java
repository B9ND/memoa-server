package org.example.memoaserver.domain.report.entity.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReportStatus {
    REPORTED("REPORTED"),
    IN_PROGRESS("IN_PROGRESS"),
    RESOLVED("RESOLVED");

    final String status;

    public String value() {
        return status;
    }
}