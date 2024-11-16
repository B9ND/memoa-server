package org.example.memoaserver.global.exception;

import org.example.memoaserver.global.exception.enums.ExceptionStatusCode;
import org.example.memoaserver.global.exception.enums.StatusCode;

public class JsonPassingException extends StatusException{
    public JsonPassingException() {
        super(ExceptionStatusCode.JSON_ERROR);
    }
}
