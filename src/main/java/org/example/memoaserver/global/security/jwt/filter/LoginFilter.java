package org.example.memoaserver.global.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.auth.dto.UserDTO;
import org.example.memoaserver.global.security.jwt.JwtUtil;
import org.example.memoaserver.global.security.jwt.service.RefreshTokenService;
import org.example.memoaserver.global.security.properties.JwtProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import java.io.IOException;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final JwtProperties jwtProperties;
    private final RefreshTokenService refreshTokenService;

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
    throws AuthenticationException {
        try {
            UserDTO loginRequest = objectMapper.readValue(req.getReader(), UserDTO.class);
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();

            if (!checkEmailVerification(loginRequest.getEmail())) {
                throw new RuntimeException("Invalid email address");
            }

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

            return authenticationManager.authenticate(authToken);
        } catch(IOException e) {

            throw new RuntimeException("Json passing error");
        }
    }

    public static Boolean checkEmailVerification(String email) {
        if (email == null) {
            return false;
        }

        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
