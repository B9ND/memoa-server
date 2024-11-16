package org.example.memoaserver.global.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.memoaserver.domain.user.dto.req.UserRequest;
import org.example.memoaserver.domain.user.entity.enums.Role;
import org.example.memoaserver.domain.user.exception.InvalidEmailException;
import org.example.memoaserver.domain.user.exception.LoginFailException;
import org.example.memoaserver.global.cache.RedisService;
import org.example.memoaserver.global.exception.JsonPassingException;
import org.example.memoaserver.global.security.jwt.JwtUtil;
import org.example.memoaserver.global.security.jwt.dto.res.JwtTokenResponse;
import org.example.memoaserver.global.security.jwt.enums.JwtType;
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

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final JwtProperties jwtProperties;
    private final RedisService redisService;

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public LoginFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil, JwtProperties jwtProperties, RedisService redisService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.jwtProperties = jwtProperties;
        this.redisService = redisService;
        super.setFilterProcessesUrl("/auth/login");
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            UserRequest loginRequest = objectMapper.readValue(request.getReader(), UserRequest.class);

            String email = loginRequest.getEmail();
            String password = loginRequest.getPassword();

            if (!checkEmailVerification(email)) {
                throw new InvalidEmailException();
            }

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password, null);
            return authenticationManager.authenticate(authToken);
        } catch(IOException e) {

            throw new JsonPassingException();
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

        String access = jwtUtil.createJwt(JwtType.ACCESS_TOKEN.category(), email, role, device, jwtProperties.getAccess().getExpiration());
        String refresh = jwtUtil.createJwt(JwtType.REFRESH_TOKEN.category(), email, role, device, jwtProperties.getRefresh().getExpiration());

        redisService.saveToken(device + "::" + email, refresh, jwtProperties.getRefresh().getExpiration());

        response.setContentType("application/json");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getWriter(), JwtTokenResponse.builder()
                .access(access)
                .refresh(refresh)
                .build());

        response.setStatus(HttpStatus.OK.value());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        throw new LoginFailException();
    }

    private Boolean checkEmailVerification(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
