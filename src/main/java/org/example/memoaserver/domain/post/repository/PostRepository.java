package org.example.memoaserver.domain.post.repository;


import org.example.memoaserver.domain.post.entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    List<PostEntity> findByTitleContainingOrContentContaining(String title, String content);

    @Query("SELECT p FROM post p " +
            "LEFT JOIN p.tags t " +
            "WHERE (:tags IS NULL OR t.tagName IN :tags) " +
            "AND (:search IS NULL OR p.title LIKE %:search% OR p.content LIKE %:search%) " +
            "AND (p.isReleased = true OR p.user.id = :userId) " +
            "AND (:userId IS NULL OR p.user.id IN " +
            "(SELECT f.follower.id FROM follow f WHERE f.following.id = :userId))")
    Page<PostEntity> findPostsByFilters(@Param("tags") List<String> tags,
                                        @Param("search") String search,
                                        @Param("userId") Long userId,
                                        Pageable pageable);

}
