package org.example.memoaserver.global.exception;

import org.springframework.http.HttpStatus;

public class FormException extends StatusException {
    public FormException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
    public FormException(String message, HttpStatus status) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public FormException(String message, Throwable cause, HttpStatus status) {
        super(status, message, cause);
    }

    public FormException(String message, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, message, cause);
    }

    public FormException(Throwable cause) {
        super(HttpStatus.BAD_REQUEST, cause);
    }

    public FormException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
