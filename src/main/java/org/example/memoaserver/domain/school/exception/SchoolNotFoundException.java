package org.example.memoaserver.domain.school.exception;

import org.example.memoaserver.domain.school.exception.enums.SchoolExceptionStatusCode;
import org.example.memoaserver.global.exception.StatusException;

public class SchoolNotFoundException extends StatusException {
    public SchoolNotFoundException() {
        super(SchoolExceptionStatusCode.SCHOOL_NOT_FOUND);
    }
}
