package org.example.memoaserver.global.security.jwt.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.domain.user.entity.enums.Role;
import org.example.memoaserver.global.security.jwt.JwtUtil;
import org.example.memoaserver.global.security.jwt.details.CustomUserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String accessToken;
        try {
            accessToken = req.getHeader("Authorization").replace("Bearer ", "");
        } catch (NullPointerException e) {
            filterChain.doFilter(req, res);
            return;
        }

        if (accessToken.isEmpty()) {

            filterChain.doFilter(req, res);

            return;
        }

        try {
            jwtUtil.getDevice(accessToken);
        } catch (SignatureException e) {
            PrintWriter writer = res.getWriter();
            writer.print("jwt is not vail");

            res.setStatus(HttpServletResponse.SC_FORBIDDEN);

            return;
        }

        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {

            PrintWriter writer = res.getWriter();
            writer.print("access token expired");

            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            return;
        }

        String category = jwtUtil.getCategory(accessToken);

        if (!category.equals("access")) {

            PrintWriter writer = res.getWriter();
            writer.print("invalid access token");

            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String email = jwtUtil.getEmail(accessToken);
        Role role = Role.valueOf(jwtUtil.getRole(accessToken));

        CustomUserDetails customUserDetails = new CustomUserDetails(UserEntity.builder()
                .email(email)
                .role(role)
                .build());

        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(req, res);
    }
}