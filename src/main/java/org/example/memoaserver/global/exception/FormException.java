package org.example.memoaserver.global.exception;

public class FormException extends RuntimeException {
    public FormException(String message) {
        super(message);
    }

    public FormException(String message, Throwable cause) {
        super(message, cause);
    }

    public FormException(Throwable cause) {
        super(cause);
    }

    public FormException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public FormException() {
        super();
    }
}
