package org.example.memoaserver.domain.image.exception;

import org.example.memoaserver.global.exception.FormException;
import org.springframework.http.HttpStatus;

public class ImageFormException extends FormException {
    public ImageFormException(String message) {
        super(message);
    }

    public ImageFormException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}