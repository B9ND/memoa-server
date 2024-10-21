package org.example.memoaserver.domain.user.dto.req;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;

@Getter
public class RegisterRequest {
    private String email;
    private String nickname;
    private String password;
    @Parameter(description = "학교(학과/학년) 모델 아이디", example = "1")
    private Long departmentId;
}
