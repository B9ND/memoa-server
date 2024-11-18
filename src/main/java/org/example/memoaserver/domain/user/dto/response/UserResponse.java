package org.example.memoaserver.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.example.memoaserver.domain.school.dto.response.DepartmentResponse;
import org.example.memoaserver.domain.user.entity.UserEntity;

@Getter
@Builder
public class UserResponse {
    private String email;
    private String nickname;
    private String description;
    private String profileImage;
    private DepartmentResponse department;
    private boolean isFollowed;

    public static UserResponse fromUserEntity(UserEntity userEntity) {
        return UserResponse.builder()
                .email(userEntity.getEmail())
                .profileImage(userEntity.getProfileImage())
                .description(userEntity.getDescription())
                .nickname(userEntity.getNickname())
                .department(DepartmentResponse.fromDepartmentEntity(userEntity.getDepartment()))
                .build();
    }

    public static UserResponse fromUserEntity(UserEntity userEntity, boolean isFollowed) {
        return UserResponse.builder()
                .email(userEntity.getEmail())
                .profileImage(userEntity.getProfileImage())
                .description(userEntity.getDescription())
                .nickname(userEntity.getNickname())
                .department(DepartmentResponse.fromDepartmentEntity(userEntity.getDepartment()))
                .isFollowed(isFollowed)
                .build();
    }
}
