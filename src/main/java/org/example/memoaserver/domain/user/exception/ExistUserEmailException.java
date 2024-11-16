package org.example.memoaserver.domain.user.exception;

import org.example.memoaserver.domain.user.exception.enums.UserExceptionStatusCode;
import org.example.memoaserver.global.exception.StatusException;

public class ExistUserEmailException extends StatusException {
    public ExistUserEmailException() {
        super(UserExceptionStatusCode.ALREADY_EXIST);
    }
}
