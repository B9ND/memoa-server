package org.example.memoaserver.global.exception;

public class JwtSignatureException extends RuntimeException {
    public JwtSignatureException(String message) {
        super(message);
    }
    public JwtSignatureException(String message, Throwable cause) {
        super(message, cause);
    }
    public JwtSignatureException(Throwable cause) {
        super(cause);
    }
    public JwtSignatureException() {
        super();
    }
}
