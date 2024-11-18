package org.example.memoaserver.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "유저 정보 수정시 모델")
@Getter @Setter
public class UpdateUserRequest {
    @Schema(description = "사용자 닉네임", example = "김민규")
    private String nickname;
    @Schema(description = "사용자 설명", example = "바인드 서버 개발자 입니다.")
    private String description;
    @Schema(description = "사용자 프로필 이미지", example = "http://example.com/200")
    private String profileImage;
    @Schema(description = "사용자 학과 아이디", example = "1")
    private Long department;
    @Schema(description = "새 비밀번호", example = "12345")
    private String password;
    @Schema(description = "이전 비밀번호", example = "1234")
    private String pastPassword;
}
