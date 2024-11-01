package org.example.memoaserver.global.exception.handler;

import org.example.memoaserver.domain.user.exception.FollowerException;
import org.example.memoaserver.global.exception.dto.res.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class FollowerExceptionHandler {
    @ExceptionHandler(FollowerException.class)
    public ResponseEntity<ErrorResponse> handleFollowerException(FollowerException ex) {
        return new ResponseEntity<>(new ErrorResponse("팔로잉/워 관련 오류", ex.getMessage()), ex.getStatus());
    }
}
