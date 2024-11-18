package org.example.memoaserver.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(name = "회원가입 요청 모델")
@Getter
public class RegisterRequest {
    @Schema(description = "인증된 유저의 이메일", example = "b1nd@naver.com")
    private String email;
    @Schema(description = "유저의 닉네임", example = "b9nd")
    private String nickname;
    @Schema(description = "유저의 비밀번호", example = "1234")
    private String password;
    @Schema(description = "학교(학과/학년)의 아이디 (학교검색에서 나온 id)", example = "1")
    private Long departmentId;
}
