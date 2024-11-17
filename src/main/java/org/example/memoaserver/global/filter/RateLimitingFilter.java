package org.example.memoaserver.global.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.global.cache.RateLimiterService;
import org.example.memoaserver.global.exception.ErrorResponseSender;
import org.example.memoaserver.global.exception.enums.ExceptionStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RateLimitingFilter extends OncePerRequestFilter {
    private final RateLimiterService rateLimiterService;
    private final ErrorResponseSender errorResponseSender;

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String clientIp = request.getRemoteAddr();

        if (!rateLimiterService.isAllowed(clientIp)) {
            errorResponseSender.sendErrorResponse(response, ExceptionStatusCode.NOT_ACCEPTABLE);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
