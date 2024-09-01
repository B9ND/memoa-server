package org.example.memoaserver.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.user.dto.FollowDTO;
import org.example.memoaserver.domain.user.dto.UserDTO;
import org.example.memoaserver.domain.user.entity.FollowEntity;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.domain.user.repository.FollowRepository;
import org.example.memoaserver.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public void addFollower(UserDTO user, UserDTO follower) {
        Long userId = userRepository.findByEmail(user.getEmail()).getId();
        Long followId = userRepository.findByEmail(follower.getEmail()).getId();

        FollowEntity followEntity = new FollowEntity();
        followEntity.setId(followId);
        followEntity.setFollower(userId);

        followRepository.save(followEntity);
    }
}