package org.example.memoaserver.global.exception.handler;

import org.example.memoaserver.domain.user.exception.RegisterFormException;
import org.example.memoaserver.domain.user.exception.VerifyCodeException;
import org.example.memoaserver.global.exception.JwtSignatureException;
import org.example.memoaserver.global.exception.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {
    @ExceptionHandler(JwtSignatureException.class)
    public ResponseEntity<ErrorResponse> handleJwtSignatureException(JwtSignatureException ex) {
        return new ResponseEntity<>(new ErrorResponse("토큰 생성 실패", ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RegisterFormException.class)
    public ResponseEntity<ErrorResponse> handleRegisterFormException(RegisterFormException ex) {
        return new ResponseEntity<>(new ErrorResponse("이메일 오류", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(VerifyCodeException.class)
    public ResponseEntity<ErrorResponse> handleVerifyCodeException(VerifyCodeException ex) {
        return new ResponseEntity<>(new ErrorResponse("인증코드 오류", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
