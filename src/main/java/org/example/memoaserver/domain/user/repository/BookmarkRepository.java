package org.example.memoaserver.domain.user.repository;

import org.example.memoaserver.domain.user.entity.BookmarkEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Long> {
    Optional<BookmarkEntity> findByPostId(Long postId);
}
