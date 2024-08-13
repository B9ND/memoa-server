package org.example.memoaserver.global.security.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class DetailsAuthDTO {
    private String id;
    private String password;
    private String email;
    private String role;
}
