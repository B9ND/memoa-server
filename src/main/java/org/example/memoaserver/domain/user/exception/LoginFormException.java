package org.example.memoaserver.domain.user.exception;

import org.example.memoaserver.global.exception.FormException;
import org.springframework.http.HttpStatus;

public class LoginFormException extends FormException {
    public LoginFormException(String message) {
        super(message);
    }

    public LoginFormException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
