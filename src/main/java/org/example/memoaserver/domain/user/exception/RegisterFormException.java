package org.example.memoaserver.domain.user.exception;

import org.example.memoaserver.global.exception.FormException;
import org.springframework.http.HttpStatus;

public class RegisterFormException extends FormException {
    public RegisterFormException(String message) {
        super(message);
    }

    public RegisterFormException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
