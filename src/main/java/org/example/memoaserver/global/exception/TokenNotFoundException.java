package org.example.memoaserver.global.exception;

import org.example.memoaserver.global.exception.enums.StatusCode;
import org.example.memoaserver.global.exception.enums.TokenExceptionStatusCode;

public class TokenNotFoundException extends StatusException {
    public TokenNotFoundException() {
        super(TokenExceptionStatusCode.REFRESH_NOT_FOUND);
    }
}
