package org.example.memoaserver.global.exception;

import org.example.memoaserver.global.exception.enums.TokenExceptionStatusCode;

public class TokenInvalidException extends StatusException {
    public TokenInvalidException() {
        super(TokenExceptionStatusCode.INVALID_TOKEN);
    }
}
