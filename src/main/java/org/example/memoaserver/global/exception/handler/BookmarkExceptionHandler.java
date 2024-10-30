package org.example.memoaserver.global.exception.handler;

import org.example.memoaserver.domain.bookmark.exception.BookmarkException;
import org.example.memoaserver.global.exception.dto.res.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BookmarkExceptionHandler {
    @ExceptionHandler(BookmarkException.class)
    public ResponseEntity<ErrorResponse> handleBookmarkException(BookmarkException ex) {
        return new ResponseEntity<>(new ErrorResponse("북마크 관련 오류", ex.getMessage()), ex.getStatus());
    }
}
