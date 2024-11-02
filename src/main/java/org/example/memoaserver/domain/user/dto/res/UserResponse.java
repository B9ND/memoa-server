package org.example.memoaserver.domain.user.dto.res;

import lombok.Builder;
import lombok.Getter;
import org.example.memoaserver.domain.school.dto.res.DepartmentResponse;
import org.example.memoaserver.domain.school.entity.DepartmentEntity;
import org.example.memoaserver.domain.user.entity.UserEntity;

@Getter
@Builder
public class UserResponse {
    private String email;
    private String nickname;
    private String description;
    private String profileImage;
    private DepartmentResponse department;

    public static UserResponse fromUserEntity(UserEntity userEntity) {
        return UserResponse.builder()
                .email(userEntity.getEmail())
                .profileImage(userEntity.getProfileImage())
                .description(userEntity.getDescription())
                .nickname(userEntity.getNickname())
                .department(DepartmentResponse.fromDepartmentEntity(userEntity.getDepartment()))
                .build();
    }
}
