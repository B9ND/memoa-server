package org.example.memoaserver.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.memoaserver.domain.school.entity.DepartmentEntity;
import org.example.memoaserver.domain.school.exception.SchoolNotFoundException;
import org.example.memoaserver.domain.school.repository.DepartmentRepository;
import org.example.memoaserver.domain.user.dto.request.RegisterRequest;
import org.example.memoaserver.domain.user.dto.request.UpdateUserRequest;
import org.example.memoaserver.domain.user.dto.response.UserResponse;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.domain.user.exception.*;
import org.example.memoaserver.domain.user.support.EmailValidator;
import org.example.memoaserver.global.security.jwt.support.UserAuthHolder;
import org.example.memoaserver.domain.user.repository.UserRepository;
import org.example.memoaserver.global.cache.RedisService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
            userRepository.findByNickname(nickname).orElseThrow(UserNotfoundException::new),
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
            toBuilder.department(departmentRepository.findById(
                updateUser.getDepartment())
                    .orElseThrow(SchoolNotFoundException::new)
            );
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

    public UserResponse register(RegisterRequest register) {
        String email = register.getEmail();

        emailCheck(email);

        UserEntity user = UserEntity.fromUserEntity(register, bCryptPasswordEncoder.encode(register.getPassword()), getDepartment(register));

        userRepository.save(user);
        redisService.deleteOnRedisForAuthenticEmail(email);

        return UserResponse.fromUserEntity(user);
    }

    private DepartmentEntity getDepartment(RegisterRequest user) {
        return departmentRepository.findById(user.getDepartmentId()).orElse(null);
    }

    private Boolean checkVerification(String email) {
        return redisService.findOnRedisForAuthenticEmail(email);
    }

    private void emailCheck(String email) {
        if (EmailValidator.isValidEmail(email)) {
            throw new InvalidEmailException();
        }
        if (userRepository.existsByEmail(email)) {
            throw new ExistUserEmailException();
        }
        if (!checkVerification(email)) {
            throw new VerifyEmailException();
        }
    }
}
