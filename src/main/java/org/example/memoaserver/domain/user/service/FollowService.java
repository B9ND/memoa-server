package org.example.memoaserver.domain.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.memoaserver.domain.user.dto.res.UserResponse;
import org.example.memoaserver.domain.user.entity.FollowEntity;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.domain.user.repository.FollowRepository;
import org.example.memoaserver.domain.user.repository.UserAuthHolder;
import org.example.memoaserver.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;
    private final UserAuthHolder userAuthHolder;

    public void addFollower(String follower) {
        UserEntity userEntity = userRepository.findByEmail(userAuthHolder.current().getEmail());
        UserEntity followerEntity = userRepository.findByNickname(follower).orElseThrow(RuntimeException::new);

        followRepository.save(FollowEntity.builder()
                .following(userEntity)
                .follower(followerEntity)
                .build());
    }

    public void removeFollower(String follower) {
        UserEntity userId = userRepository.findByEmail(userAuthHolder.current().getEmail());
        UserEntity followId = userRepository.findByNickname(follower).orElseThrow(RuntimeException::new);
        followRepository.deleteByFollowingAndFollower(userId, followId);
    }

    public List<UserResponse> getFollowers(String user) {
        List<UserEntity> users =  followRepository.findAllByFollowing(userRepository.findByNickname(user).orElseThrow(RuntimeException::new))
                .stream()
                .map(FollowEntity::getFollower)
                .toList();

        return users.stream().map(UserResponse::fromUserEntity).toList();
    }

    public List<UserResponse> getFollowings(String user) {
        List<UserEntity> users =  followRepository.findAllByFollower(userRepository.findByNickname(user).orElseThrow(RuntimeException::new))
                .stream()
                .map(FollowEntity::getFollowing)
                .toList();

        return users.stream().map(UserResponse::fromUserEntity).toList();
    }
}