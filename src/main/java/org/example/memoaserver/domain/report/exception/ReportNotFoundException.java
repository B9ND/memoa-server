package org.example.memoaserver.domain.report.exception;

import org.example.memoaserver.domain.report.exception.enums.ReportExceptionStatusCode;
import org.example.memoaserver.global.exception.StatusException;

public class ReportNotFoundException extends StatusException {
    public ReportNotFoundException() {
        super(ReportExceptionStatusCode.REPORT_NOT_FOUND);
    }
}
