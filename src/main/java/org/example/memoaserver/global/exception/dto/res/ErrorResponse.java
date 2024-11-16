package org.example.memoaserver.global.exception.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.memoaserver.global.dto.response.Response;
import org.example.memoaserver.global.exception.enums.StatusCode;

@Getter
public final class ErrorResponse extends Response {
    private final String message;

    @Builder
    public ErrorResponse(int status, String code, String message) {
        super(status, code);
        this.message = message;
    }

    public static ErrorResponse errorResponse(StatusCode status) {
        return ErrorResponse.builder()
                .status(status.getStatusCode())
                .code(status.getExceptionName())
                .message(status.getMessage())
                .build();
    }

    public static ErrorResponse of(int status, String code, String message) {
        return ErrorResponse.builder()
            .message(message)
            .status(status)
            .code(code)
            .build();
    }
}
