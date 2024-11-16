package org.example.memoaserver.global.exception;

import org.example.memoaserver.global.exception.enums.TokenExceptionStatusCode;

public class TokenExpiredException extends StatusException {
    public TokenExpiredException() {
        super(TokenExceptionStatusCode.TOKEN_EXPIRED);
    }
}
