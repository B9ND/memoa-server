package org.example.memoaserver.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.global.exception.enums.ExceptionStatusCode;
import org.example.memoaserver.global.exception.enums.StatusCode;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class StatusException extends RuntimeException {
    private final StatusCode status;
}
