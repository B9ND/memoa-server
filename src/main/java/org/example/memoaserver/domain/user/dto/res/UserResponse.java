package org.example.memoaserver.domain.user.dto.res;

import lombok.Builder;
import lombok.Getter;
import org.example.memoaserver.domain.user.entity.UserEntity;

@Getter
@Builder
public class UserResponse {
    private String email;
    private String nickname;
    private String profileImage;

    public static UserResponse fromUserEntity(UserEntity userEntity) {
        return UserResponse.builder()
                .email(userEntity.getEmail())
                .profileImage(userEntity.getProfileImage())
                .nickname(userEntity.getNickname())
                .build();
    }
}
