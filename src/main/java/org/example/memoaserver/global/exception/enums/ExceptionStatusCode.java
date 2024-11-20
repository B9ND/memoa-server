package org.example.memoaserver.global.exception.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExceptionStatusCode implements StatusCode {
    NOT_FOUND(404, "존재하지 않는 데이터"),
    BAD_REQUEST(400, "존재하지 않는 요청"),
    NOT_ACCEPTABLE(406, "데이터 접근권한 없음"),
    JSON_ERROR(401, "파싱할 수 없는 json 객체"),
    AlREADY_CREATED_DATA(409, "이미 존재하는 데이터"),
    JWT_SIGNATURE(400, "jwt 서명 오류"),
    PAYLOAD_TOO_LARGE(413, "너무 큰 데이터"),
    ENCODE_FAILED(500, "인코딩 실패"),
    PROXY_ERROR(502, "프록시 에러"),
    REQUIRE_ARGUMENTS(400, "필수 항목 입력 안됨"),
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
