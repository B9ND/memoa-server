package org.example.memoaserver.global.exception;

import org.springframework.http.HttpStatus;

public class TokenException extends StatusException{
    public TokenException(String message) {
        super(HttpStatus.CONFLICT, message);
    }


}
