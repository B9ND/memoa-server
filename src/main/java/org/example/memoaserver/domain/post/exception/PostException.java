package org.example.memoaserver.domain.post.exception;

import org.example.memoaserver.global.exception.StatusException;
import org.springframework.http.HttpStatus;

public class PostException extends StatusException {
    public PostException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public PostException(String message, HttpStatus httpStatus) {
        super(httpStatus, message);
    }

    public PostException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
