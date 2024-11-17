package org.example.memoaserver.domain.post.exception.enums;

import lombok.AllArgsConstructor;
import org.example.memoaserver.global.exception.enums.StatusCode;

@AllArgsConstructor
public enum PostExceptionStatusCode implements StatusCode {
    POST_NOT_FOUND(404, "찾을 수 없는 게시물");

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
