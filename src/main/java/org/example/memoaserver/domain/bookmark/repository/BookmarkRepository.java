package org.example.memoaserver.domain.bookmark.repository;

import org.example.memoaserver.domain.post.entity.PostEntity;
import org.example.memoaserver.domain.bookmark.entity.BookmarkEntity;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Long> {
    Optional<List<BookmarkEntity>> findByUserOrderByCreatedAtDesc(UserEntity user);
    void deleteByUserAndPost(UserEntity user, PostEntity post);
    Boolean existsByUserAndPost(UserEntity user, PostEntity post);
}