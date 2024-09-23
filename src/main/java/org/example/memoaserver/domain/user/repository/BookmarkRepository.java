package org.example.memoaserver.domain.user.repository;

import org.example.memoaserver.domain.post.entity.PostEntity;
import org.example.memoaserver.domain.user.entity.BookmarkEntity;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<BookmarkEntity, Long> {

    BookmarkEntity findByUsernameAndPost(UserEntity user, PostEntity post);

    void deleteByUserAndPost(UserEntity user, PostEntity post);

}