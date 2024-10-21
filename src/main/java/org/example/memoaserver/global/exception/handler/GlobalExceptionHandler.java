package org.example.memoaserver.global.exception.handler;

import io.jsonwebtoken.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.example.memoaserver.domain.user.exception.RegisterFormException;
import org.example.memoaserver.global.exception.JwtSignatureException;
import org.example.memoaserver.global.exception.dto.ErrorResponse;
import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.security.NoSuchAlgorithmException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoSuchAlgorithmException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchAlgorithmException(NoSuchAlgorithmException ex) {
        return new ResponseEntity<>(new ErrorResponse("인코딩 실패", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(PropertyValueException.class)
    public ResponseEntity<ErrorResponse> handlePropertyValueException(PropertyValueException ex) {
        return new ResponseEntity<>(new ErrorResponse("필수 항목이 입력되지 않았습니다.", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return new ResponseEntity<>(new ErrorResponse("이미 존재하는 데이터입니다.", ex.getMessage()), HttpStatus.CONFLICT);
    }

    @ExceptionHandler({RedisConnectionFailureException.class, IOException.class})
    public ResponseEntity<ErrorResponse> handleRedisConnectionFailureException(RedisConnectionFailureException ex) {
        return new ResponseEntity<>(new ErrorResponse("서버 오류", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        return new ResponseEntity<>(new ErrorResponse("필수 파라미터가 포함되지 않았습니다.", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }



//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
//        ErrorResponse errorResponse;
//
//        if (ex.getMessage().contains("already exists school")) {
//            errorResponse = new ErrorResponse("이미 존재하는 학교입니다.", ex.getMessage());
//            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
//        }
//
//        errorResponse = new ErrorResponse("서버 오류 발생", null);
//        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}