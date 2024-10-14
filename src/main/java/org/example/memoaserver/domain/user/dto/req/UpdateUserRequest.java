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
    @ApiModelProperty(value = "새 비밀번호", example = "12345")
    private String password;
    @ApiModelProperty(value = "이전 비밀번호", example = "1234")
    private String pastPassword;
}
