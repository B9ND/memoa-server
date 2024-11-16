package org.example.memoaserver.domain.image.exception;

import org.example.memoaserver.domain.image.exception.enums.ImageExceptionStatusCode;
import org.example.memoaserver.global.exception.StatusException;

public class ImageTypeException extends StatusException {
    public ImageTypeException(ImageExceptionStatusCode statusCode) {
        super(statusCode);
    }
}