package org.example.memoaserver.global.security.jwt.filter;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.user.exception.InvalidEmailException;
import org.example.memoaserver.global.exception.ErrorResponseSender;
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
    ) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        } catch (JwtException | IllegalArgumentException ex) {
//            setErrorResponse(response, "토큰 관련 오류", ex.getMessage());
        } catch (InvalidEmailException ex) {
//            setErrorResponse(response, "로그인 오류", ex.getMessage());
        }
    }
}
