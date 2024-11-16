package org.example.memoaserver.global.exception;

import org.example.memoaserver.global.exception.enums.TokenExceptionStatusCode;
import org.springframework.http.HttpStatus;

public class TokenCategoryNotFoundException extends StatusException{
    public TokenCategoryNotFoundException() {
        super(TokenExceptionStatusCode.CATEGORY_NOT_FOUND);
    }
}
