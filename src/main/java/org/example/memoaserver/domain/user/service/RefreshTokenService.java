package org.example.memoaserver.domain.user.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.user.dto.request.RefreshTokenRequest;
import org.example.memoaserver.domain.user.entity.enums.Role;
import org.example.memoaserver.domain.user.support.RefreshTokenValidator;
import org.example.memoaserver.global.cache.RedisService;
import org.example.memoaserver.global.security.jwt.JwtUtil;
import org.example.memoaserver.global.security.jwt.dto.res.JwtTokenResponse;
import org.example.memoaserver.global.security.properties.JwtProperties;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RedisService redisService;
    private final JwtUtil jwtUtil;
    private final JwtProperties jwtProperties;
    private final RefreshTokenValidator refreshTokenValidator;

    public JwtTokenResponse reissue(HttpServletRequest request, RefreshTokenRequest tokenRequest) {
        String device = request.getHeader("User-Agent") + "_" + request.getRemoteAddr();
        String refresh = tokenRequest.getRefresh();

        refreshTokenValidator.validate(device, refresh);

        return getRefreshInfo(refresh, device);
    }

    public void logout(HttpServletRequest request, RefreshTokenRequest tokenRequest) {
        String device = request.getHeader("User-Agent") + "_" + request.getRemoteAddr();

        refreshTokenValidator.validate(device, tokenRequest.getRefresh());
        deleteTokenByEmail(device, jwtUtil.getEmail(tokenRequest.getRefresh()));
    }

    private void deleteTokenByEmail(String device, String email) {
        redisService.deleteOnRedisForToken(device + "::" + email);
    }

    private JwtTokenResponse getRefreshInfo(String refresh, String device) {
        return createJwt(Role.valueOf(jwtUtil.getRole(refresh)), jwtUtil.getEmail(refresh), device);
    }

    private JwtTokenResponse createJwt(Role role, String email, String device) {
        String newAccess = jwtUtil.createJwt("access", email, role, device, jwtProperties.getAccess().getExpiration());
        String newRefresh = jwtUtil.createJwt("refresh", email, role, device, jwtProperties.getRefresh().getExpiration());

        redisService.saveToken(device + "::" + email, newRefresh, jwtProperties.getRefresh().getExpiration());
        return JwtTokenResponse.builder().access(newAccess).refresh(newRefresh).build();

    }
}