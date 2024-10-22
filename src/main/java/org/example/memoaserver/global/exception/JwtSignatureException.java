package org.example.memoaserver.global.exception;

import org.springframework.http.HttpStatus;

public class JwtSignatureException extends StatusException {
    public JwtSignatureException(String message) {

        super(HttpStatus.UNAUTHORIZED, message);
    }
}
