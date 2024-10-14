package org.example.memoaserver.domain.user.dto.res;

import lombok.Builder;
import lombok.Getter;
import org.example.memoaserver.domain.user.entity.UserEntity;

@Getter
@Builder
public class UserRes {
    private String email;
    private String nickname;

    public static UserRes fromUserEntity(UserEntity userEntity) {
        return UserRes.builder()
                .email(userEntity.getEmail())
                .nickname(userEntity.getNickname())
                .build();
    }
}
