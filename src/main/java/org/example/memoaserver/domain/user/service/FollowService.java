package org.example.memoaserver.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.user.dto.UserDTO;
import org.example.memoaserver.domain.user.entity.FollowEntity;
import org.example.memoaserver.domain.user.repository.FollowRepository;
import org.example.memoaserver.domain.user.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public void addFollower(String user, String follower) {
        Long userId = userRepository.findByEmail(user).getId();
        Long followId = userRepository.findByEmail(follower).getId();

        FollowEntity followEntity = new FollowEntity();
        followEntity.setFollowing(userId);
        followEntity.setFollower(followId);

        followRepository.save(followEntity);
    }

    public void removeFollower(String user, String follower) {
        Long userId = userRepository.findByEmail(user).getId();
        Long followId = userRepository.findByEmail(follower).getId();
        followRepository.deleteByFollowingAndFollower(userId, followId);
    }

    // 팔로우 조회 (현재 목록 보내주기)
    public List<UserDTO> getFollowers(String user, ResponseEntity<?> followers) {
        return followRepository.findAllByFollowing(userRepository.findByEmail(user).getId()).stream()
                .map(followEntity -> UserDTO.of(userRepository.findById(followEntity.getId())))
                .toList();
    }
}