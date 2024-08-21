package org.example.memoaserver.global.security.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class JwtTokenDTO {
    private String access;
    private String refresh;
}
