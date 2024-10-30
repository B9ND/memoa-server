package org.example.memoaserver.domain.bookmark.exception;

import org.example.memoaserver.global.exception.StatusException;
import org.springframework.http.HttpStatus;

public class BookmarkException extends StatusException {
    public BookmarkException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public BookmarkException() {
      super(HttpStatus.BAD_REQUEST);
    }
}
