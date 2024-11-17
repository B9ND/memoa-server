package org.example.memoaserver.domain.image.exception.enums;

import lombok.AllArgsConstructor;
import org.example.memoaserver.global.exception.enums.StatusCode;

@AllArgsConstructor
public enum ImageExceptionStatusCode implements StatusCode {
    UNSUPPORTED_MEDIA_TYPE(415, "지원되지 않는 파일 형식"),
    NO_FILE_TYPE(415, "파일 확장자가 표시되어야함"),
    UPLOAD_FAILED(500, "업로드에 실패함"),
    IMAGE_NOT_FOUND(404, "이미지를 찾을 수 없음");

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
