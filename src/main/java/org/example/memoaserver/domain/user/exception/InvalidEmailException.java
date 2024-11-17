package org.example.memoaserver.domain.user.exception;

import org.example.memoaserver.domain.user.exception.enums.UserExceptionStatusCode;
import org.example.memoaserver.global.exception.StatusException;

public class InvalidEmailException extends StatusException {
    public InvalidEmailException() {
        super(UserExceptionStatusCode.INVALID_EMAIL);
    }
}
