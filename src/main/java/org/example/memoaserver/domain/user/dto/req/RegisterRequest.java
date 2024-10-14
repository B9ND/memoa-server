package org.example.memoaserver.domain.user.dto.req;

import lombok.Getter;

@Getter
public class RegisterRequest {
    private String email;
    private String nickname;
    private String password;
}
