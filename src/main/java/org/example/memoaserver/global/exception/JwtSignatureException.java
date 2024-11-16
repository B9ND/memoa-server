package org.example.memoaserver.global.exception;

import org.example.memoaserver.global.exception.enums.ExceptionStatusCode;

public class JwtSignatureException extends StatusException {
    public JwtSignatureException() {

        super(ExceptionStatusCode.JWT_SIGNATURE);
    }
}
