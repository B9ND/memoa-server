package org.example.memoaserver.domain.user.exception;

import org.example.memoaserver.global.exception.FormException;

public class RegisterFormException extends FormException {
    public RegisterFormException(String message) {
        super(message);
    }
}
