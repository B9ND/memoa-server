package org.example.memoaserver.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.example.memoaserver.domain.user.entity.UserEntity;

@Getter
@Builder
public class FollowUserResponse {
    private String email;
    private String nickname;
    private String profileImage;

    public static FollowUserResponse fromUserEntity(UserEntity userEntity) {
        return FollowUserResponse.builder()
                .email(userEntity.getEmail())
                .nickname(userEntity.getNickname())
                .profileImage(userEntity.getProfileImage())
                .build();
    }
}
