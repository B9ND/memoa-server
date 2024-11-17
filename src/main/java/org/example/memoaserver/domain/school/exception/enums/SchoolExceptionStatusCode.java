package org.example.memoaserver.domain.school.exception.enums;

import lombok.AllArgsConstructor;
import org.example.memoaserver.global.exception.enums.StatusCode;

@AllArgsConstructor
public enum SchoolExceptionStatusCode implements StatusCode {
    SCHOOL_NOT_FOUND(404, "학교를 찾을 수 없음"),
    SCHOOL_EXIST(409, "이미 존재하는 학교");

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
