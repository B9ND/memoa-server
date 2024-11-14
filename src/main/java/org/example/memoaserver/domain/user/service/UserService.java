package org.example.memoaserver.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.memoaserver.domain.school.exception.NullSchoolException;
import org.example.memoaserver.domain.school.repository.DepartmentRepository;
import org.example.memoaserver.domain.user.dto.req.RegisterRequest;
import org.example.memoaserver.domain.user.dto.req.UpdateUserRequest;
import org.example.memoaserver.domain.user.dto.res.UserResponse;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.domain.user.entity.enums.Role;
import org.example.memoaserver.domain.user.exception.NullUserException;
import org.example.memoaserver.domain.user.exception.RegisterFormException;
import org.example.memoaserver.global.security.jwt.support.UserAuthHolder;
import org.example.memoaserver.domain.user.repository.UserRepository;
import org.example.memoaserver.global.cache.RedisService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final UserAuthHolder userAuthHolder;
    private final RedisService redisService;
    private final DepartmentRepository departmentRepository;
    private final FollowService followService;

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public UserResponse me() {
        return UserResponse.fromUserEntity(userAuthHolder.current());
    }

    public UserResponse findUserByNickname(String nickname) {
        return UserResponse.fromUserEntity(
            userRepository.findByNickname(nickname).orElseThrow(NullUserException::new),
            followService.isExist(userAuthHolder.current(), nickname)
        );
    }

    public UserResponse updateMe(UpdateUserRequest updateUser) {
        UserEntity userEntity = userAuthHolder.current();

        var toBuilder = userEntity.toBuilder();
        if (updateUser.getNickname() != null) {
            toBuilder.nickname(updateUser.getNickname());
        }

        if (updateUser.getDepartment() != null) {
            toBuilder.department(departmentRepository.findById(updateUser.getDepartment()).orElseThrow(NullSchoolException::new));
        }

        if (updateUser.getProfileImage() != null) {
            toBuilder.profileImage(updateUser.getProfileImage());
        }

        if (updateUser.getDescription() != null) {
            toBuilder.description(updateUser.getDescription());
        }

        if (updateUser.getPassword() != null && updateUser.getPastPassword() != null) {
            if (bCryptPasswordEncoder.matches(updateUser.getPastPassword(), userEntity.getPassword())) {
                toBuilder.password(bCryptPasswordEncoder.encode(updateUser.getPassword()));
            }
        }
        UserEntity updatedUser = toBuilder.build();
        userRepository.save(updatedUser);

        return UserResponse.fromUserEntity(updatedUser);
    }

    public UserResponse register(RegisterRequest user) {
        String email = user.getEmail();

        emailCheck(email);

        String hashedPassword = bCryptPasswordEncoder.encode(user.getPassword());

        redisService.deleteOnRedisForAuthenticEmail(email);

        UserEntity userEntity = UserEntity.builder()
                .email(email)
                .password(hashedPassword)
                .nickname(user.getNickname())
                .department(departmentRepository.findById(user.getDepartmentId()).orElse(null))
                .profileImage("https://memoa-s3.s3.ap-northeast-2.amazonaws.com/profile.jpg")
                .role(Role.ROLE_USER)
                .build();

        userRepository.save(userEntity);

        return UserResponse.fromUserEntity(userEntity);
    }

    private void emailCheck(String email) {
        if (!checkEmailVerification(email)) {
            throw new RegisterFormException("you need to use email [xxx@xxx.com]");
        }
        if (userRepository.existsByEmail(email)) {
            throw new RegisterFormException("your email already exists");
        }
        if (!checkVerification(email)) {
            throw new RegisterFormException("this email does not verify", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    private Boolean checkVerification(String email) {
        return redisService.findOnRedisForAuthenticEmail(email);
    }

    private Boolean checkEmailVerification(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
