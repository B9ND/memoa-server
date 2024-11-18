package org.example.memoaserver.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.memoaserver.domain.school.entity.DepartmentEntity;
import org.example.memoaserver.domain.user.dto.request.RegisterRequest;
import org.example.memoaserver.domain.user.entity.enums.Role;

import java.time.LocalDate;
import java.util.Date;

@Getter @SuperBuilder(toBuilder = true)
@Entity(name = "user")
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;

    private String description;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    private String password;

    @Column(columnDefinition = "TEXT")
    private String profileImage = "https://memoa-s3.s3.ap-northeast-2.amazonaws.com/profile.jpg";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id")
    private DepartmentEntity department;

    private LocalDate birth;

    public static UserEntity fromUserEntity(RegisterRequest user, String password, DepartmentEntity department) {
        return UserEntity.builder()
                .email(user.getEmail())
                .password(password)
                .nickname(user.getNickname())
                .department(department)
                .profileImage("https://memoa-s3.s3.ap-northeast-2.amazonaws.com/profile.jpg")
                .role(Role.ROLE_USER)
                .build();
    }
}