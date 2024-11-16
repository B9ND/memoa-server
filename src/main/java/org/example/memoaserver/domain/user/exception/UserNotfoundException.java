package org.example.memoaserver.domain.user.exception;

import org.example.memoaserver.domain.user.exception.enums.UserExceptionStatusCode;
import org.example.memoaserver.global.exception.StatusException;
import org.springframework.http.HttpStatus;

public class UserNotfoundException extends StatusException {
    public UserNotfoundException() {
        super(UserExceptionStatusCode.USER_NOT_FOUND);
    }
}
