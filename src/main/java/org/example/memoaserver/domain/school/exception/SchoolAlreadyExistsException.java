package org.example.memoaserver.domain.school.exception;

import org.example.memoaserver.global.exception.StatusException;
import org.springframework.http.HttpStatus;

public class SchoolAlreadyExistsException extends StatusException {
    public SchoolAlreadyExistsException(String message) {

        super(HttpStatus.CONFLICT, message);
    }

    public SchoolAlreadyExistsException(String message, Throwable cause) {

        super(HttpStatus.CONFLICT, message, cause);
    }

    public SchoolAlreadyExistsException(Throwable cause) {

        super(HttpStatus.CONFLICT, cause);
    }

    public SchoolAlreadyExistsException() {

        super(HttpStatus.CONFLICT);
    }
}
