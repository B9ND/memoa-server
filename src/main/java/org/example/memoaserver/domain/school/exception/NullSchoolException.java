package org.example.memoaserver.domain.school.exception;

import org.example.memoaserver.global.exception.StatusException;
import org.springframework.http.HttpStatus;

public class NullSchoolException extends StatusException {
    public NullSchoolException() {
        super(HttpStatus.NOT_FOUND);
    }
}
