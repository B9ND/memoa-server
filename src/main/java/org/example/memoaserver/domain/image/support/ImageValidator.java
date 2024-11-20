package org.example.memoaserver.domain.image.support;

import org.example.memoaserver.domain.image.exception.ImageTypeException;
import org.example.memoaserver.domain.image.exception.enums.ImageExceptionStatusCode;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public final class ImageValidator {
    public String getContentType(String extension) {
        return switch (extension) {
            case "jpg", "jpeg" -> "image/jpeg";
            case "png" -> "image/png";
            case "gif" -> "image/gif";
            case "webp" -> "image/webp";
            default -> "application/octet-stream";
        };
    }

    public void validateImageFileExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new ImageTypeException(ImageExceptionStatusCode.NO_FILE_TYPE);
        }

        String extension = filename.substring(lastDotIndex + 1).toLowerCase();
        List<String> allowedExtensionList = Arrays.asList("jpg", "jpeg", "png", "gif", "webp");

        if (!allowedExtensionList.contains(extension)) {
            throw new ImageTypeException(ImageExceptionStatusCode.UNSUPPORTED_MEDIA_TYPE);
        }
    }
}
