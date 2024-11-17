package org.example.memoaserver.global.exception.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TokenExceptionStatusCode implements StatusCode {
    REFRESH_NOT_FOUND(404, "refresh 토큰은 찾을 수 없습니다."),
    INVALID_TOKEN(402, "유요하지 않은 토큰"),
    TOKEN_EXPIRED(403, "토큰 만료됨"),
    CATEGORY_NOT_FOUND(404, "찾을 수 없는 카테고리");

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
