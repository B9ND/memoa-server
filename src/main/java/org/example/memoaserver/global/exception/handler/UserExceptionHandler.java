package org.example.memoaserver.global.exception.handler;

import org.example.memoaserver.domain.user.exception.NullUserException;
import org.example.memoaserver.global.exception.dto.res.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {
    @ExceptionHandler(NullUserException.class)
    public ResponseEntity<ErrorResponse> handleNullUserException(NullUserException ex) {
        return new ResponseEntity<>(new ErrorResponse("유저를 찾을 수 없음", ex.getMessage()), ex.getStatus());
    }
}
