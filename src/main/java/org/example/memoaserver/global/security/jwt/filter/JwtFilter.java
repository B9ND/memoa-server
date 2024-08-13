package org.example.memoaserver.global.security.jwt.filter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.auth.repository.UserRepository;
import org.example.memoaserver.global.security.dto.DetailsAuthDTO;
import org.example.memoaserver.global.security.jwt.JwtUtil;
import org.example.memoaserver.global.security.jwt.details.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader("Authorization");

        if (accessToken == null) {
            filterChain.doFilter(request, response);

            return;
        }

        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {
            PrintWriter printWriter = response.getWriter();
            printWriter.println("Token is expired");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        String category = jwtUtil.getCategory(accessToken);

        if (!category.equals("access")) {
            PrintWriter printWriter = response.getWriter();
            printWriter.println("Token is not access");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        Long username = Long.valueOf(jwtUtil.getUsername(accessToken));
        String role = jwtUtil.getRole(accessToken);

        Optional<String> email = userRepository.findEmailById(username);

        if (email.isEmpty()) {
            PrintWriter printWriter = response.getWriter();
            printWriter.println("User not found");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        DetailsAuthDTO userEntity = new DetailsAuthDTO();
        userEntity.setEmail(email.get());
        userEntity.setRole(role);
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
