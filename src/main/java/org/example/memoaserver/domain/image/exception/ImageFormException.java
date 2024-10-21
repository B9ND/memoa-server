package org.example.memoaserver.domain.image.exception;

import org.example.memoaserver.global.exception.FormException;

public class ImageFormException extends FormException {
    public ImageFormException(String message) {
        super(message);
    }

    public ImageFormException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageFormException(Throwable cause) {
        super(cause);
    }

    public ImageFormException() {
        super();
    }
}
