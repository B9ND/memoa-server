//package org.example.memoaserver.global.exception.handler;
//
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.security.SignatureException;
//import org.example.memoaserver.domain.user.exception.LoginFormException;
//import org.example.memoaserver.domain.user.exception.RegisterFormException;
//import org.example.memoaserver.domain.user.exception.VerifyCodeException;
//import org.example.memoaserver.global.exception.JwtSignatureException;
//import org.example.memoaserver.global.exception.TokenException;
//import org.example.memoaserver.global.exception.dto.res.ErrorResponse;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class AuthExceptionHandler {
//    @ExceptionHandler(JwtSignatureException.class)
//    public ResponseEntity<ErrorResponse> handleJwtSignatureException(JwtSignatureException ex) {
//        return new ResponseEntity<>(new ErrorResponse("토큰 생성 실패", ex.getMessage()), ex.getStatus());
//    }
//
//    @ExceptionHandler(RegisterFormException.class)
//    public ResponseEntity<ErrorResponse> handleRegisterFormException(RegisterFormException ex) {
//        return new ResponseEntity<>(new ErrorResponse("이메일 오류", ex.getMessage()), ex.getStatus());
//    }
//
//    @ExceptionHandler(VerifyCodeException.class)
//    public ResponseEntity<ErrorResponse> handleVerifyCodeException(VerifyCodeException ex) {
//        return new ResponseEntity<>(new ErrorResponse("인증코드 오류", ex.getMessage()), ex.getStatus());
//    }
//
//    @ExceptionHandler(LoginFormException.class)
//    public ResponseEntity<ErrorResponse> handleLoginFormException(LoginFormException ex) {
//        return new ResponseEntity<>(new ErrorResponse("로그인 오류", ex.getMessage()), ex.getStatus());
//    }
//
//    @ExceptionHandler(MalformedJwtException.class)
//    public ResponseEntity<ErrorResponse> handleMalformedJwtException(MalformedJwtException ex) {
//        return new ResponseEntity<>(new ErrorResponse("jwt 를 파싱할 수 없습니다.", ex.getMessage()), HttpStatus.CONFLICT);
//    }
//
//    @ExceptionHandler(SignatureException.class)
//    public ResponseEntity<ErrorResponse> handleSignatureException(SignatureException ex) {
//        return new ResponseEntity<>(new ErrorResponse("jwt 오류", ex.getMessage()), HttpStatus.CONFLICT);
//    }
//
//    @ExceptionHandler(TokenException.class)
//    public ResponseEntity<ErrorResponse> handleTokenException(TokenException ex) {
//        return new ResponseEntity<>(new ErrorResponse("리프레시 토큰 오류", ex.getMessage()), HttpStatus.CONFLICT);
//    }
//}
