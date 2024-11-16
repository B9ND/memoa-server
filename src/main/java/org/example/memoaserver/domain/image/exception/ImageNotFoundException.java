package org.example.memoaserver.domain.image.exception;

import org.example.memoaserver.domain.image.exception.enums.ImageExceptionStatusCode;
import org.example.memoaserver.global.exception.StatusException;

public class ImageNotFoundException extends StatusException {
    public ImageNotFoundException() {
        super(ImageExceptionStatusCode.IMAGE_NOT_FOUND);
    }
}
