package org.example.memoaserver.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateUserDTO {
    private String id;
    private String email;
    private String nickname;
    private String password;
    private String pastPassword;
}
