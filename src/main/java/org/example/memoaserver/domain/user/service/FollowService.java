package org.example.memoaserver.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.example.memoaserver.domain.user.dto.UserDTO;
import org.example.memoaserver.domain.user.entity.FollowEntity;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.domain.user.repository.FollowRepository;
import org.example.memoaserver.domain.user.repository.UserAuthHolder;
import org.example.memoaserver.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final UserAuthHolder userAuthHolder;

    // 팔로우
    public void addFollower(String user, String follower) {
        UserEntity userEntity = userRepository.findByEmail(user);
        UserEntity followerEntity = userRepository.findByEmail(follower);

        FollowEntity followEntity = FollowEntity.builder()
                .following(userEntity)
                .follower(followerEntity)
                .build();

        followRepository.save(followEntity);
    }

    // 언팔로우
    public void removeFollower(String follower) {
        UserEntity user = userRepository.findByEmail(userAuthHolder.current().getEmail());
        UserEntity follow = userRepository.findByEmail(follower);
        followRepository.deleteByFollowingAndFollower(user, follow);
    }

    // 팔로잉 조회 (현재 목록 보내주기)
    public List<UserDTO> getFollowers(String user) {
        return followRepository.findAllByFollower(userRepository.findByEmail(user)).stream()
                .map(followEntity -> UserDTO.of(userRepository.findById(followEntity.getId())))
                .toList();
    }
}