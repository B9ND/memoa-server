package org.example.memoaserver.global.security.jwt.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
@AllArgsConstructor
public class JwtTokenResponse {
    private String access;
    private String refresh;
}
