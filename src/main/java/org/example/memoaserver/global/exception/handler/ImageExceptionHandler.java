package org.example.memoaserver.global.exception.handler;

import org.example.memoaserver.domain.image.exception.ImageFormException;
import org.example.memoaserver.domain.image.exception.ImageUploadException;
import org.example.memoaserver.global.exception.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ImageExceptionHandler {
    @ExceptionHandler(ImageFormException.class)
    public ResponseEntity<ErrorResponse> handleImageFormException(ImageFormException ex) {
        return new ResponseEntity<>(new ErrorResponse("이미지 폼 형식에 맞지 않습니다.", ex.getMessage()), ex.getStatus());
    }

    @ExceptionHandler(ImageUploadException.class)
    public ResponseEntity<ErrorResponse> handleImageUploadException(ImageUploadException ex) {
        return new ResponseEntity<>(new ErrorResponse("이미지 업로드에 실패했습니다.", ex.getMessage()), ex.getStatus());
    }
}
