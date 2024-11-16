package org.example.memoaserver.domain.user.exception.enums;

import lombok.AllArgsConstructor;
import org.example.memoaserver.global.exception.enums.StatusCode;

@AllArgsConstructor
public enum UserExceptionStatusCode implements StatusCode {
    INVALID_EMAIL(400, "유효하지 않은 이메일"),
    LOGIN_FAIL(401, "아이디 또는 비밀번호 불일치"),
    USER_NOT_FOUND(404, "유저를 찾을 수 없음"),
    EMAIL_VERIFY_FAILED(401, "인증되지 않은 이메일"),
    INVALID_CODE(401, "코드 인증 실패"),
    ALREADY_EXIST(409, "이미 존재하는 이메일");

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
