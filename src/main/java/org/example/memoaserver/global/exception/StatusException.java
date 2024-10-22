package org.example.memoaserver.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class StatusException extends RuntimeException {
    private final HttpStatus status;

    public StatusException(HttpStatus status) {
        super();
        this.status = status;
    }

    public StatusException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public StatusException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public StatusException(HttpStatus status, Throwable cause) {
        super(cause);
        this.status = status;
    }
}
