package org.example.memoaserver.global.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.domain.user.repository.UserRepository;
import org.example.memoaserver.global.exception.TokenInvalidException;
import org.example.memoaserver.global.security.jwt.details.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserEntity userData = userRepository.findByEmail(email);

        if (userData == null) {
            throw new TokenInvalidException();
        }
        return new CustomUserDetails(userData);
    }
}
