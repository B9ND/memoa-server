package org.example.memoaserver.domain.user.dto.req;

import lombok.Getter;

@Getter
public class LoginRequest {
    private String email;
    private String password;
}
