package org.example.memoaserver.global.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.user.dto.UserDTO;
import org.example.memoaserver.domain.user.entity.enums.Role;
import org.example.memoaserver.domain.user.exception.LoginFormException;
import org.example.memoaserver.global.cache.RedisService;
import org.example.memoaserver.global.security.jwt.JwtUtil;
import org.example.memoaserver.global.security.jwt.dto.res.JwtTokenResponse;
import org.example.memoaserver.global.security.properties.JwtProperties;
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
    private final RedisService redisService;

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
                throw new LoginFormException("Invalid email");
            }

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);
            return authenticationManager.authenticate(authToken);
        } catch(IOException e) {

            throw new LoginFormException("Json passing error", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        String email = authentication.getName();

        String device = request.getHeader("User-Agent") + "_" + request.getRemoteAddr();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        Role role = Role.valueOf(auth.getAuthority());

        String access = jwtUtil.createJwt("access", email, role, device, jwtProperties.getAccess().getExpiration());
        String refresh = jwtUtil.createJwt("refresh", email, role, device, jwtProperties.getRefresh().getExpiration());

        JwtTokenResponse jwtTokenResponse = JwtTokenResponse.builder()
                .access(access)
                .refresh(refresh)
                .build();

        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), jwtTokenResponse);

        redisService.saveToken(device + "::" + email, refresh, jwtProperties.getRefresh().getExpiration());

        response.setStatus(HttpStatus.OK.value());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        throw new LoginFormException(failed.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    private Boolean checkEmailVerification(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
