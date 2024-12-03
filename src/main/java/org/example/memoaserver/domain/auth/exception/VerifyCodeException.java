package org.example.memoaserver.domain.auth.exception;

import org.example.memoaserver.domain.user.exception.enums.UserExceptionStatusCode;
import org.example.memoaserver.global.exception.StatusException;

public class VerifyCodeException extends StatusException {
    public VerifyCodeException() {
        super(UserExceptionStatusCode.INVALID_CODE);
    }
}
