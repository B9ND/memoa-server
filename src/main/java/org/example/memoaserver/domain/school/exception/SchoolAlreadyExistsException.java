package org.example.memoaserver.domain.school.exception;

import org.example.memoaserver.domain.school.exception.enums.SchoolExceptionStatusCode;
import org.example.memoaserver.global.exception.StatusException;
import org.springframework.http.HttpStatus;

public class SchoolAlreadyExistsException extends StatusException {
    public SchoolAlreadyExistsException() {

        super(SchoolExceptionStatusCode.SCHOOL_EXIST);
    }
}
