package org.example.memoaserver.global.exception.handler;

import io.jsonwebtoken.io.IOException;
import org.example.memoaserver.global.exception.StatusException;
import org.example.memoaserver.global.exception.dto.res.ErrorResponse;
import org.example.memoaserver.global.exception.enums.ExceptionStatusCode;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.NoSuchAlgorithmException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(StatusException.class)
    public ResponseEntity<ErrorResponse> handleClassCastException(StatusException ex) {
        return ResponseEntity
                .status(ex.getStatus().getStatusCode())
                .body(ErrorResponse.errorResponse(ex.getStatus()));
    }

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
        return ResponseEntity
            .status(409)
            .body(ErrorResponse.errorResponse(ExceptionStatusCode.AlREADY_CREATED_DATA));
    }

    @ExceptionHandler({RedisConnectionFailureException.class, IOException.class})
    public ResponseEntity<ErrorResponse> handleRedisConnectionFailureException(RedisConnectionFailureException ex) {
        return ResponseEntity
            .status(500)
            .body(ErrorResponse.errorResponse(ExceptionStatusCode.INTERNAL_SERVER));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return ResponseEntity
            .status(400)
            .body(ErrorResponse.errorResponse(ExceptionStatusCode.REQUIRE_ARGUMENTS));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity
            .status(400)
            .body(ErrorResponse.errorResponse(ExceptionStatusCode.REQUIRE_ARGUMENTS));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        return ResponseEntity
            .status(400)
            .body(ErrorResponse.errorResponse(ExceptionStatusCode.REQUIRE_ARGUMENTS));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        return ResponseEntity
            .status(400)
            .body(ErrorResponse.errorResponse(ExceptionStatusCode.BAD_REQUEST));
    }

    @ExceptionHandler(ClassCastException.class)
    public ResponseEntity<ErrorResponse> handleClassCastException(ClassCastException ex) {
        return ResponseEntity
            .status(502)
            .body(ErrorResponse.errorResponse(ExceptionStatusCode.PROXY_ERROR));
    }
}