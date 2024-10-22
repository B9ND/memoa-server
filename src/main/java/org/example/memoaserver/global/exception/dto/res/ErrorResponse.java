package org.example.memoaserver.global.exception.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private String details;
}
