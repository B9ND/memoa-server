package org.example.memoaserver.domain.user.service;

import org.example.memoaserver.domain.user.dto.UpdateUserDTO;
import org.example.memoaserver.domain.user.dto.UserDTO;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.domain.user.repository.UserAuthHolder;
import org.example.memoaserver.domain.user.repository.UserRepository;
import org.example.memoaserver.global.exception.CustomConflictException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final RedisTemplate<String, Object> redisTemplate2;
    private final UserAuthHolder userAuthHolder;

    public UserService(@Qualifier("redisTemplate2")
                           RedisTemplate<String, Object> redisTemplate2,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       UserRepository userRepository, UserAuthHolder userAuthHolder) {
        this.redisTemplate2 = redisTemplate2;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepository = userRepository;
        this.userAuthHolder = userAuthHolder;
    }

    private static final String EMAIL_PATTERN =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public UserDTO me() {
        UserEntity userEntity = userAuthHolder.current();
        return UserDTO.of(userEntity);
    }

    public UserDTO updateMe(UpdateUserDTO updateUser) {
        UserEntity userEntity = userAuthHolder.current();
        if (updateUser.getNickname() != null) {
            userEntity.setNickname(updateUser.getNickname());
        }

        if (updateUser.getPassword() != null && updateUser.getPastPassword() != null) {
            if (bCryptPasswordEncoder.matches(updateUser.getPastPassword(), userEntity.getPassword())) {
                userEntity.setPassword(bCryptPasswordEncoder.encode(updateUser.getPassword()));
            }
        }

        userRepository.save(userEntity);

        return UserDTO.of(userEntity);
    }

    public UserEntity register(UserDTO userDTO) {
        String email = userDTO.getEmail();
        String hashedPassword = bCryptPasswordEncoder.encode(userDTO.getPassword());

        if (!checkEmailVerification(email)) {
            throw new CustomConflictException("you need to use email [xxx@xxx.com]");
        }

        if (userRepository.existsByEmail(email)) {
            throw new CustomConflictException("your email already exists");
        }

        if (!checkVerification(email)) {
            throw new CustomConflictException("this email does not verify");
        }

        redisTemplate2.delete(email);

        UserEntity userEntity = new UserEntity();

        userEntity.setEmail(email);
        userEntity.setPassword(hashedPassword);
        userEntity.setRole("ROLE_USER");

        return userRepository.save(userEntity);
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    private Boolean checkVerification(String email) {
        return redisTemplate2.hasKey(email);
    }

    private Boolean checkEmailVerification(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
