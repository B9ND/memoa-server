package org.example.memoaserver.domain.user.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.memoaserver.domain.user.dto.response.FollowUserResponse;
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

        return followRepository.findByFollower(user)
                .stream()
                .map(this::fromFollowerUser)
                .toList();
    }

    private FollowUserResponse fromFollowerUser(FollowEntity followedUser) {
        return FollowUserResponse.fromUserEntity(followedUser.getFollowing(), isFollowed(followedUser.getFollowing()));
    }

    // nickname 이 팔로우 하는 사람들
    public List<FollowUserResponse> getFollowings(String nickname) {
        UserEntity user = (nickname != null) ? userRepository.findByNickname(nickname).orElseThrow(UserNotfoundException::new) : userAuthHolder.current();

        return followRepository.findByFollowing(user)
                .stream()
                .map(this::fromFollowingUser)
                .toList();
    }

    private FollowUserResponse fromFollowingUser(FollowEntity followedUser) {
        return FollowUserResponse.fromUserEntity(followedUser.getFollower(), isFollowed(followedUser.getFollower()));
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
            me,
            userRepository
            .findByNickname(nickname)
            .orElseThrow(UserNotfoundException::new)
        );
    }

    private Boolean isFollowed(UserEntity follower) {
        return followRepository.existsByFollowingAndFollower(userAuthHolder.current(), follower);
    }

    private void addFollower(UserEntity me, String nickname) {
        UserEntity followerEntity = userRepository.findByNickname(nickname).orElseThrow(UserNotfoundException::new);

        followRepository.save(FollowEntity.builder()
                .following(me)
                .follower(followerEntity)
                .build());
    }

    private void removeFollower(UserEntity me, String nickname) {
        UserEntity follower = userRepository.findByNickname(nickname).orElseThrow(UserNotfoundException::new);
        followRepository.deleteByFollowingAndFollower(me, follower);
    }
}