package org.example.memoaserver.domain.user.repository;

import org.example.memoaserver.domain.user.entity.FollowEntity;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FollowRepository extends JpaRepository<FollowEntity, Long> {
    void deleteByFollowingAndFollower(Long following, Long follower);
    List<FollowEntity> findAllByFollowing(Long following);
    List<FollowEntity> findAllByFollower(Long follower);
}