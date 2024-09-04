package org.example.memoaserver.global.exception;

public class SchoolAlreadyExistsException extends RuntimeException {
    public SchoolAlreadyExistsException(String message) {

        super(message);
    }

    public SchoolAlreadyExistsException(String message, Throwable cause) {

        super(message, cause);
    }

    public SchoolAlreadyExistsException(Throwable cause) {

        super(cause);
    }

    public SchoolAlreadyExistsException() {

        super();
    }
}
