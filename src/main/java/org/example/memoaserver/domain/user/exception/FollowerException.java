package org.example.memoaserver.domain.user.exception;

import org.example.memoaserver.global.exception.StatusException;
import org.springframework.http.HttpStatus;

public class FollowerException extends StatusException {
    public FollowerException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public FollowerException(String message, HttpStatus status) {
        super(status, message);
    }

    public FollowerException() {
        super(HttpStatus.NOT_FOUND);
    }

    public FollowerException(HttpStatus status) {
        super(status);
    }

    public FollowerException(String message, Throwable cause) {
        super(HttpStatus.NOT_FOUND, message, cause);
    }

    public FollowerException(Throwable cause) {
        super(HttpStatus.NOT_FOUND, cause);
    }
}
