package org.example.memoaserver.domain.post.exception;

import org.example.memoaserver.domain.post.exception.enums.PostExceptionStatusCode;
import org.example.memoaserver.global.exception.StatusException;

public class PostNotFoundException extends StatusException {
    public PostNotFoundException() {
        super(PostExceptionStatusCode.POST_NOT_FOUND);
    }
}
