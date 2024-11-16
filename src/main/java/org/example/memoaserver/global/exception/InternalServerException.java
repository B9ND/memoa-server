package org.example.memoaserver.global.exception;

import org.example.memoaserver.global.exception.enums.ExceptionStatusCode;

public class InternalServerException extends StatusException {
    public InternalServerException() {
        super(ExceptionStatusCode.INTERNAL_SERVER);
    }
}
