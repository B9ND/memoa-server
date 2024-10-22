package org.example.memoaserver.global.exception.handler;

import org.example.memoaserver.domain.school.exception.SchoolAlreadyExistsException;
import org.example.memoaserver.global.exception.dto.res.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class SchoolExceptionHandler {
    @ExceptionHandler(SchoolAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleSchoolAlreadyExistsException(SchoolAlreadyExistsException ex) {
        return new ResponseEntity<>(new ErrorResponse("학교 에러", ex.getMessage()), ex.getStatus());
    }
}
