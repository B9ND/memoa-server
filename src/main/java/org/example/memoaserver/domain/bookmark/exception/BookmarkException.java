package org.example.memoaserver.domain.bookmark.exception;

import org.example.memoaserver.domain.bookmark.exception.enums.BookmarkExceptionStatusCode;
import org.example.memoaserver.global.exception.StatusException;

public class BookmarkException extends StatusException {
    public BookmarkException() {
        super(BookmarkExceptionStatusCode.BOOKMARK_NOT_FOUND);
    }
}
