package org.example.memoaserver.domain.auth.repository;

import org.example.memoaserver.domain.auth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<String> findEmailById(Long id);
}
