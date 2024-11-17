package org.example.memoaserver.global.security.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.user.repository.UserRepository;
import org.example.memoaserver.global.security.jwt.JwtUtil;
import org.example.memoaserver.global.security.jwt.details.CustomUserDetails;
import org.example.memoaserver.global.security.jwt.enums.JwtType;
import org.example.memoaserver.global.security.jwt.support.TokenExtractor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String accessToken = TokenExtractor.extract(request, JwtType.ACCESS_TOKEN);

        if (!accessToken.isEmpty()) {
            setAuthentication(accessToken);
        }
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String accessToken) {
        SecurityContextHolder.getContext().setAuthentication(createAuthentication(accessToken));
    }

    private Authentication createAuthentication(String accessToken) {
        CustomUserDetails user = findUserDetails(accessToken);
        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    private CustomUserDetails findUserDetails(String accessToken) {
        return new CustomUserDetails(userRepository.findByEmail(jwtUtil.getEmail(accessToken)));
    }
}