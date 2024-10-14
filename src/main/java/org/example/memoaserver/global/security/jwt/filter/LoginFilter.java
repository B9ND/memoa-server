package org.example.memoaserver.global.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.user.dto.UserDTO;
import org.example.memoaserver.domain.user.entity.enums.Role;
import org.example.memoaserver.global.security.jwt.JwtUtil;
import org.example.memoaserver.global.security.jwt.dto.JwtTokenDTO;
import org.example.memoaserver.global.security.properties.JwtProperties;
import org.example.memoaserver.global.service.RefreshTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final JwtProperties jwtProperties;
    private final RefreshTokenService refreshTokenService;

    private static final String EMAIL_PATTERN =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            UserDTO loginRequest = objectMapper.readValue(request.getReader(), UserDTO.class);

            String email = loginRequest.getEmail();
            String password = loginRequest.getPassword();

            if (!checkEmailVerification(email)) {
                throw new RuntimeException("Invalid email");
            }

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);
            return authenticationManager.authenticate(authToken);
        } catch(IOException e) {

            throw new RuntimeException("Json passing error");
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {

        String email = authentication.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        Role role = Role.valueOf(auth.getAuthority());

        String access = jwtUtil.createJwt("access", email, role, jwtProperties.getAccess().getExpiration());
        String refresh = jwtUtil.createJwt("refresh", email, role, jwtProperties.getRefresh().getExpiration());

        JwtTokenDTO jwtTokenDTO = new JwtTokenDTO((access), refresh);

        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), jwtTokenDTO);

        refreshTokenService.addRefreshEntity(email, refresh, jwtProperties.getRefresh().getExpiration());

        response.setStatus(HttpStatus.OK.value());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }

    private Boolean checkEmailVerification(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
