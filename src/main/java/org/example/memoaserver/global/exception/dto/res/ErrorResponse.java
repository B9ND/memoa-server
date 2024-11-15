package org.example.memoaserver.global.exception.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.example.memoaserver.global.dto.response.Response;

@Getter
public class ErrorResponse extends Response {
    private String message;

    @Builder
    private ErrorResponse(int status, String code, String message) {
        super(status, code);
        this.message = message;
    }

    public static ErrorResponse of(int status, String code, String message) {
        return ErrorResponse.builder()
            .message(message)
            .status(status)
            .code(code)
            .build();
    }
}
