package org.example.memoaserver.domain.user.exception;

public class VerifyCodeException extends RuntimeException {
    public VerifyCodeException(String message) {
        super(message);
    }

    public VerifyCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public VerifyCodeException(Throwable cause) {
        super(cause);
    }

    public VerifyCodeException() {
        super();
    }
}
