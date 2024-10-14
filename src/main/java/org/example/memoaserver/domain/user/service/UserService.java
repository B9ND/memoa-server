package org.example.memoaserver.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.user.dto.req.UpdateUserRequest;
import org.example.memoaserver.domain.user.dto.req.RegisterRequest;
import org.example.memoaserver.domain.user.dto.res.UserResponse;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.domain.user.entity.enums.Role;
import org.example.memoaserver.domain.user.repository.UserAuthHolder;
import org.example.memoaserver.domain.user.repository.UserRepository;
import org.example.memoaserver.global.cache.RedisService;
import org.example.memoaserver.global.exception.CustomConflictException;
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

    private static final String EMAIL_PATTERN =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public UserResponse me() {
        return UserResponse.fromUserEntity(userAuthHolder.current());
    }

    public UserResponse updateMe(UpdateUserRequest updateUser) {
        UserEntity userEntity = userAuthHolder.current();

        var toBuilder = userRepository.findByEmail(userEntity.getEmail()).toBuilder();
        if (updateUser.getNickname() != null) {
            toBuilder.nickname(updateUser.getNickname());
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
        String hashedPassword = bCryptPasswordEncoder.encode(user.getPassword());

        if (!checkEmailVerification(email)) {
            throw new CustomConflictException("you need to use email [xxx@xxx.com]");
        }

        if (userRepository.existsByEmail(email)) {
            throw new CustomConflictException("your email already exists");
        }

        if (!checkVerification(email)) {
            throw new CustomConflictException("this email does not verify");
        }

        redisService.deleteOnRedisForAuthenticEmail(email);

        UserEntity userEntity = UserEntity.builder()
                .email(email)
                .password(hashedPassword)
                .nickname(user.getNickname())
                .role(Role.ROLE_USER)
                .build();

        return UserResponse.fromUserEntity(userRepository.save(userEntity));
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private Boolean checkVerification(String email) {
        return redisService.findOnRedisForAuthenticEmail(email);
    }

    private Boolean checkEmailVerification(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
