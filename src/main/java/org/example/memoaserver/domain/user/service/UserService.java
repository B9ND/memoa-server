package org.example.memoaserver.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.school.repository.DepartmentRepository;
import org.example.memoaserver.domain.user.dto.req.UpdateUserRequest;
import org.example.memoaserver.domain.user.dto.req.RegisterRequest;
import org.example.memoaserver.domain.user.dto.res.UserResponse;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.domain.user.entity.enums.Role;
import org.example.memoaserver.domain.user.exception.RegisterFormException;
import org.example.memoaserver.domain.user.repository.UserAuthHolder;
import org.example.memoaserver.domain.user.repository.UserRepository;
import org.example.memoaserver.global.cache.RedisService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final UserAuthHolder userAuthHolder;
    private final RedisService redisService;
    private final DepartmentRepository departmentRepository;

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public UserResponse me() {
        return UserResponse.fromUserEntity(userRepository.findByEmail(userAuthHolder.current().getEmail()));
    }

    public UserResponse updateMe(UpdateUserRequest updateUser) {
        UserEntity userEntity = userRepository.findByEmail(userAuthHolder.current().getEmail());

        var toBuilder = userEntity.toBuilder();
        if (updateUser.getNickname() != null) {
            toBuilder.nickname(updateUser.getNickname());
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
                .role(Role.ROLE_USER)
                .build();

        return UserResponse.fromUserEntity(userRepository.save(userEntity));
    }

    private void emailCheck(String email) {
        if (!checkEmailVerification(email)) {
            throw new RegisterFormException("you need to use email [xxx@xxx.com]");
        }
        if (userRepository.existsByEmail(email)) {
            throw new RegisterFormException("your email already exists");
        }
        if (!checkVerification(email)) {
            throw new RegisterFormException("this email does not verify");
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
