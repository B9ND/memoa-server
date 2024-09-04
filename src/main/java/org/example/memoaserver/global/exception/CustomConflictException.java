package org.example.memoaserver.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CustomConflictException extends RuntimeException {
    public CustomConflictException(String message, Throwable cause) {

        super(message, cause);
    }

    public CustomConflictException(String message) {

        super(message);
    }

    public CustomConflictException(Throwable cause) {

        super(cause);
    }

    public CustomConflictException() {

        super();
    }

}
