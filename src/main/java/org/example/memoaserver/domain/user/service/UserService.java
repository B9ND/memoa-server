package org.example.memoaserver.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.user.dto.UserDTO;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.domain.user.repository.UserRepository;
import org.example.memoaserver.global.exception.CustomConflictException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    public UserEntity register(UserDTO userDTO) {
        String email = userDTO.getEmail();
        String rawPassword = userDTO.getPassword();
        String hashedPassword = bCryptPasswordEncoder.encode(rawPassword);

        if (!email.split("-")[0].equals("010")) {
            throw new CustomConflictException("your phone number must start with 010");
        }

        email = email.replaceAll("-", "");

        System.out.println(email);

        Boolean isExist = userRepository.existsByEmail(email);

        if (isExist) {
            throw new CustomConflictException("your phone number already exists");
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setEmail(email);
        userEntity.setPassword(hashedPassword);
        userEntity.setRole("ROLE_USER");

        return userRepository.save(userEntity);
    }
}
