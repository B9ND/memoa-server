package org.example.memoaserver.global.security.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.global.exception.ErrorResponseSender;
import org.example.memoaserver.global.exception.StatusException;
import org.example.memoaserver.global.exception.enums.ExceptionStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtExceptionHandlerFilter extends OncePerRequestFilter {
    private final ErrorResponseSender errorResponseSender;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) {
        try{
            filterChain.doFilter(request, response);
        } catch (StatusException ex) {
            errorResponseSender.sendErrorResponse(response, ex.getStatus());
        } catch (Exception ex) {
            errorResponseSender.sendErrorResponse(response, ExceptionStatusCode.INTERNAL_SERVER);
        }
    }
}
