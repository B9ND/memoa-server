package org.example.memoaserver.domain.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.memoaserver.domain.user.entity.UserEntity;

import java.util.Optional;

@ApiModel(description = "유저 정보 모델")
@Getter
@NoArgsConstructor
public class UserDTO {
    private String id;
    @ApiModelProperty(value = "사용자 이메일", example = "example@example.com")
    private String email;
    @ApiModelProperty(value = "사용자 닉네임", example = "김민규")
    private String nickname;
    @ApiModelProperty(value = "사용자 비밀번호", example = "1234")
    private String password;

    @Builder
    public UserDTO(String id, String email, String nickname, String password) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public static UserDTO of(Optional<UserEntity> userEntity) {
        return UserDTO.builder()
                .email(userEntity.get().getEmail())
                .nickname(userEntity.get().getNickname())
                .build();
    }
}