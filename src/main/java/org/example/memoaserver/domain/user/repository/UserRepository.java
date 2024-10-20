package org.example.memoaserver.domain.user.repository;

import org.example.memoaserver.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByEmail(String email);

    UserEntity findById(long id);

    UserEntity findByEmail(String email);

    Optional<UserEntity> findByNickname(String nickname);
}
