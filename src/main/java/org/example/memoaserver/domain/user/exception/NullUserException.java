package org.example.memoaserver.domain.user.exception;

import org.example.memoaserver.global.exception.StatusException;
import org.springframework.http.HttpStatus;

public class NullUserException extends StatusException {
    public NullUserException() {
        super(HttpStatus.NOT_FOUND);
    }
}
