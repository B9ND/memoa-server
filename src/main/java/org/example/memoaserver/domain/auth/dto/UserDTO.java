package org.example.memoaserver.domain.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDTO {
    private String username;
    private String password;
    private String email;
    private String age;
    private String birth;
    private String nickname;
    private String role;
}
