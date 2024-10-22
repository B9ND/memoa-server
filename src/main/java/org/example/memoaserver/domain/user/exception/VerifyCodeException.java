package org.example.memoaserver.domain.user.exception;

import org.example.memoaserver.global.exception.StatusException;
import org.springframework.http.HttpStatus;

public class VerifyCodeException extends StatusException {
    public VerifyCodeException(String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }

    public VerifyCodeException(String message, Throwable cause, HttpStatus httpStatus) {
        super(httpStatus, message, cause);
    }

    public VerifyCodeException(String message, HttpStatus httpStatus) {
        super(httpStatus, message);
    }

    public VerifyCodeException(String message, Throwable cause) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message, cause);
    }

    public VerifyCodeException(Throwable cause) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, cause);
    }

    public VerifyCodeException() {
        super(HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
