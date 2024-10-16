package org.example.memoaserver.domain.user.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(description = "유저 정보 수정시 모델")
@Getter @Setter
public class UpdateUserRequest {
    @ApiModelProperty(value = "사용자 닉네임", example = "김민규")
    private String nickname;
    @ApiModelProperty(value = "사용자 설명", example = "바인드 서버 개발자 입니다.")
    private String description;
    @ApiModelProperty(value = "사용자 프로필 이미지", example = "http://example.com/200")
    private String profileImage;
    @ApiModelProperty(value = "새 비밀번호", example = "12345")
    private String password;
    @ApiModelProperty(value = "이전 비밀번호", example = "1234")
    private String pastPassword;
}
