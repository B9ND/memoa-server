package org.example.memoaserver.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.user.dto.UserDTO;
import org.example.memoaserver.domain.user.entity.FollowEntity;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.domain.user.repository.FollowRepository;
import org.example.memoaserver.domain.user.repository.UserAuthHolder;
import org.example.memoaserver.domain.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final UserAuthHolder userAuthHolder;

    public void addFollower(String user, String follower) {
        UserEntity userEntity = userRepository.findByEmail(user);
        UserEntity followerEntity = userRepository.findByEmail(follower);

        followRepository.save(FollowEntity.builder()
                .following(userEntity)
                .follower(followerEntity)
                .build());
    }

    public void removeFollower(String follower) {
        UserEntity userId = userRepository.findByEmail(userAuthHolder.current().getEmail());
        UserEntity followId = userRepository.findByEmail(follower);
        followRepository.deleteByFollowingAndFollower(userId, followId);
    }

    public List<UserDTO> getFollowers(String user) {
        return followRepository.findAllByFollower(userRepository.findByEmail(user)).stream()
                .map(followEntity -> UserDTO.of(userRepository.findById(followEntity.getId())))
                .toList();
    }
}