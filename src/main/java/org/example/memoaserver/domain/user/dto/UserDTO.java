package org.example.memoaserver.domain.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.memoaserver.domain.user.entity.UserEntity;

import java.util.Optional;

@Getter
@NoArgsConstructor
public class UserDTO {
    private String id;
    private String email;
    private String nickname;
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