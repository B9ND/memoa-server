package org.example.memoaserver.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDTO {
    private String email;
    private String nickname;
    private String password;
}