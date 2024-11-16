package org.example.memoaserver.domain.user.exception;

import org.example.memoaserver.domain.user.exception.enums.UserExceptionStatusCode;
import org.example.memoaserver.global.exception.StatusException;

public class VeriftyEmailException extends StatusException {
    public VeriftyEmailException() {
        super(UserExceptionStatusCode.EMAIL_VERIFY_FAILED);
    }
}
