package org.example.memoaserver.global.exception.handler;

import io.jsonwebtoken.io.IOException;
import org.example.memoaserver.global.exception.StatusException;
import org.example.memoaserver.global.exception.dto.res.ErrorResponse;
import org.example.memoaserver.global.exception.enums.ExceptionStatusCode;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.NoSuchAlgorithmException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoSuchAlgorithmException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchAlgorithmException(NoSuchAlgorithmException ex) {
        return ResponseEntity
            .status(500)
            .body(ErrorResponse.errorResponse(ExceptionStatusCode.ENCODE_FAILED));
    }

    @ExceptionHandler(PropertyValueException.class)
    public ResponseEntity<ErrorResponse> handlePropertyValueException(PropertyValueException ex) {
        return ResponseEntity
            .status(ExceptionStatusCode.REQUIRE_ARGUMENTS.getStatusCode())
            .body(ErrorResponse.errorResponse(ExceptionStatusCode.REQUIRE_ARGUMENTS));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return new ResponseEntity<>(new ErrorResponse("이미 존재하는 데이터입니다.", ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({RedisConnectionFailureException.class, IOException.class})
    public ResponseEntity<ErrorResponse> handleRedisConnectionFailureException(RedisConnectionFailureException ex) {
        return new ResponseEntity<>(new ErrorResponse("서버 오류", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(new ErrorResponse("필수 인자가 포함되지 않음", "body가 없음"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(new ErrorResponse("필수값이 공백일 수 없습니다.", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        return new ResponseEntity<>(new ErrorResponse("필수 파라미터가 포함되지 않았습니다.", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return ErrorResponse(409, )
    }

    @ExceptionHandler(ClassCastException.class)
    public ResponseEntity<ErrorResponse> handleClassCastException(ClassCastException ex) {
        return new ResponseEntity<>(new ErrorResponse("클래스 변환이 불가능합니다.", ex.getMessage()), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(StatusException.class)
    public ResponseEntity<ErrorResponse> handleClassCastException(StatusException ex) {
        return ResponseEntity
            .status(ex.getStatus().getStatusCode())
            .body(ErrorResponse.errorResponse(ex.getStatus()));
    }
}