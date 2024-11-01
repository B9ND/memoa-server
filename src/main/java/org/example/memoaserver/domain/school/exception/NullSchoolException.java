package org.example.memoaserver.domain.school.exception;

import org.example.memoaserver.global.exception.StatusException;
import org.springframework.http.HttpStatus;

public class SchoolNoneException extends StatusException {
    public SchoolNoneException() {
        super(HttpStatus.NOT_FOUND);
    }
}
