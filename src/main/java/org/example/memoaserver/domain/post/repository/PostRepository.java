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

    @Query( "SELECT DISTINCT p FROM post p " +
            "LEFT JOIN p.tags t " +
            "WHERE (:tags IS NULL OR t.tagName IN :tags) " +
            "AND (:search IS NULL OR p.title LIKE CONCAT('%', :search, '%') OR p.content LIKE CONCAT('%', :search, '%')) " +
            "AND (p.isReleased = true OR p.user.id = :userId) " +
            "ORDER BY " +
            "CASE " +
            "    WHEN (:search IS NOT NULL AND (p.title LIKE CONCAT('%', :search, '%') OR p.content LIKE CONCAT('%', :search, '%'))) THEN 1 " +
            "    WHEN (:tags IS NOT NULL AND t.tagName IN :tags) THEN 2 " +
            "    ELSE 3 " +
            "END, " +
            "p.createdAt DESC")
    Page<PostEntity> findPostsByFilters(@Param("tags") List<String> tags,
                                        @Param("search") String search,
                                        @Param("userId") Long userId,
                                        Pageable pageable);



    @Query( "SELECT DISTINCT p FROM post p " +
            "LEFT JOIN p.tags t " +
            "WHERE (:userId IS NULL OR p.user.id IN " +
            "(SELECT f.follower.id FROM follow f WHERE f.following.id = :userId))" +
            "ORDER BY p.createdAt ASC")
    Page<PostEntity> findPostsByFollower(@Param("userId") Long userId, Pageable pageable);
}
