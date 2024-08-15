package org.example.memoaserver.global.exception.handler;

import org.example.memoaserver.global.exception.JwtSignatureException;
import org.example.memoaserver.global.exception.dto.ErrorResponse;
import org.hibernate.PropertyValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(JwtSignatureException.class)
    public ResponseEntity<?> handleJwtSignatureException(JwtSignatureException ex) {
        return new ResponseEntity<>("create token failed", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PropertyValueException.class)
    public ResponseEntity<?> handlePropertyValueException(PropertyValueException ex) {
        ErrorResponse errorResponse = new ErrorResponse("필수 항목이 입력되지 않았습니다.", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ErrorResponse errorResponse = new ErrorResponse("이미 존재하는 데이터입니다.", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        ErrorResponse errorResponse;

        if (ex.getMessage().contains("already exists school")) {
            errorResponse = new ErrorResponse("이미 존재하는 학교입니다.", ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        }

        errorResponse = new ErrorResponse("서버 오류 발생", null);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
