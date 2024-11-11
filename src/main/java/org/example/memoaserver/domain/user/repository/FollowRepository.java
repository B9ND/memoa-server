package org.example.memoaserver.domain.user.repository;

import org.example.memoaserver.domain.user.entity.FollowEntity;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<FollowEntity, Long> {
    void deleteByFollowingAndFollower(UserEntity following, UserEntity follower);
    List<FollowEntity> findByFollowing(UserEntity following);
    List<FollowEntity> findByFollower(UserEntity follower);
    Boolean existsByFollowingAndFollower(UserEntity following, UserEntity follower);
}