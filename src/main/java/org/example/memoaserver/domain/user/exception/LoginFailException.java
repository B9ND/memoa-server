package org.example.memoaserver.domain.user.exception;

import org.example.memoaserver.domain.user.exception.enums.UserExceptionStatusCode;
import org.example.memoaserver.global.exception.StatusException;

public class LoginFailException extends StatusException {
    public LoginFailException() {
        super(UserExceptionStatusCode.LOGIN_FAIL);
    }
}
