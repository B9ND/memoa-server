package org.example.memoaserver.domain.image.exception;

import org.example.memoaserver.domain.image.exception.enums.ImageExceptionStatusCode;
import org.example.memoaserver.global.exception.StatusException;

public class ImageUploadException extends StatusException {
    public ImageUploadException() {
        super(ImageExceptionStatusCode.UPLOAD_FAILED);
    }
}