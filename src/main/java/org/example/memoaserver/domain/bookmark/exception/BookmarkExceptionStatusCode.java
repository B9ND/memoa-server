package org.example.memoaserver.domain.bookmark.exception;

import lombok.AllArgsConstructor;
import org.example.memoaserver.global.exception.enums.StatusCode;

@AllArgsConstructor
public enum BookmarkExceptionStatusCode implements StatusCode {
    BOOKMARK_NOT_FOUND(404, "존재하지 않는 북마크");

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
