package org.example.memoaserver.domain.user.repository;

import org.example.memoaserver.domain.post.entity.PostEntity;
import org.example.memoaserver.domain.user.entity.BookmarkEntity;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Long> {
    Optional<BookmarkEntity> findByUserAndPost(UserEntity user, PostEntity post);
//    Optional<BookmarkEntity> findByPostId(Long postId);
}
