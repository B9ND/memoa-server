package org.example.memoaserver.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDTO {
    private String id;
    private String email;
    private String nickname;
    private String password;
}