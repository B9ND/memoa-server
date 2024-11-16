package org.example.memoaserver.domain.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.memoaserver.domain.user.dto.res.FollowUserResponse;
import org.example.memoaserver.domain.user.entity.FollowEntity;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.example.memoaserver.domain.user.exception.UserNotfoundException;
import org.example.memoaserver.domain.user.repository.FollowRepository;
import org.example.memoaserver.global.security.jwt.support.UserAuthHolder;
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

    // nickname 을 팔로우 하는 사람들
    public List<FollowUserResponse> getFollowers(String nickname) {
        UserEntity user = (nickname != null) ? userRepository.findByNickname(nickname).orElseThrow(UserNotfoundException::new) : userAuthHolder.current();

        return followRepository.findByFollowing(user)
                .stream()
                .map(FollowEntity::getFollower)
                .map(FollowUserResponse::fromUserEntity)
                .toList();
    }

    // nickname 이 팔로우 하는 사람들
    public List<FollowUserResponse> getFollowings(String nickname) {
        UserEntity user = (nickname != null) ? userRepository.findByNickname(nickname).orElseThrow(UserNotfoundException::new) : userAuthHolder.current();

        return followRepository.findByFollower(user)
                .stream()
                .map(FollowEntity::getFollowing)
                .map(FollowUserResponse::fromUserEntity)
                .toList();
    }

    @Transactional
    public void addOrDeleteFollower(String nickname) {
        UserEntity me = userAuthHolder.current();
        if (isExist(me, nickname)) {
            removeFollower(me, nickname);
            return;
        }
        addFollower(me, nickname);
    }

    public Boolean isExist(UserEntity me, String nickname) {
        return followRepository.existsByFollowingAndFollower(
            userRepository
                .findByNickname(nickname)
                .orElseThrow(UserNotfoundException::new),
            me
        );
    }

    private void addFollower(UserEntity me, String nickname) {
        UserEntity followerEntity = userRepository.findByNickname(nickname).orElseThrow(UserNotfoundException::new);

        followRepository.save(FollowEntity.builder()
                .following(followerEntity)
                .follower(me)
                .build());
    }

    private void removeFollower(UserEntity me, String nickname) {
        UserEntity follower = userRepository.findByNickname(nickname).orElseThrow(UserNotfoundException::new);
        followRepository.deleteByFollowingAndFollower(follower, me);
    }
}