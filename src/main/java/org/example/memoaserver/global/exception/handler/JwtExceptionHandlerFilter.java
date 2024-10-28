package org.example.memoaserver.global.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.memoaserver.global.exception.dto.res.ErrorResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        } catch (JwtException | IllegalArgumentException e) {
            setErrorResponse(response, "토큰 관련 오류", e.getMessage());
        }
    }
    private void setErrorResponse(
            HttpServletResponse response,
            String errorMessage,
            String details
            ) {
        ObjectMapper objectMapper = new ObjectMapper();

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try{
            response.getWriter().write(objectMapper.writeValueAsString(getErrorResponse(errorMessage, details)));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private ErrorResponse getErrorResponse(String errorMessage, String details) {
        return ErrorResponse.builder()
                .message(errorMessage)
                .details(details)
                .build();
    }
}
