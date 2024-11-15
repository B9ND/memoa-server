package org.example.memoaserver.global.exception.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExceptionStatusCode implements StatusCode {
    NOT_FOUND(404, "존재하지 않는 데이터"),
    BAD_REQUEST(400, "존재하지 않는 요청"),
    NOT_ACCEPTABLE(406, "데이터 접근권한 없음"),
    INTERNAL_SERVER(500, "서버 오류");

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
