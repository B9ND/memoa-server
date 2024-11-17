package org.example.memoaserver.domain.user.exception;

import org.example.memoaserver.domain.user.exception.enums.UserExceptionStatusCode;
import org.example.memoaserver.global.exception.StatusException;

public class VerifyEmailException extends StatusException {
    public VerifyEmailException() {
        super(UserExceptionStatusCode.EMAIL_VERIFY_FAILED);
    }
}
