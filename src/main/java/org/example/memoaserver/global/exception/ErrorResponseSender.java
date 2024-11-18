package org.example.memoaserver.global.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.global.exception.dto.res.ErrorResponse;
import org.example.memoaserver.global.exception.enums.StatusCode;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public final class ErrorResponseSender {
    private final ObjectMapper objectMapper;

    public void sendErrorResponse(
        HttpServletResponse response,
        StatusCode statusCode
    ) {
        try{
            if (!response.isCommitted()) {
                response.setStatus(statusCode.getStatusCode());
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(objectMapper.writeValueAsString(errorMessage(statusCode)));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private ErrorResponse errorMessage(StatusCode statusCode) {
        return ErrorResponse.of(
                statusCode.getStatusCode(),
                statusCode.getExceptionName(),
                statusCode.getMessage()
        );
    }
}
