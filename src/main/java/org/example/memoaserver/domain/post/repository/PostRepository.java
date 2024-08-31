package org.example.memoaserver.domain.post.repository;

import org.example.memoaserver.domain.post.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    List<PostEntity> findByCreatedAtBetween(LocalDate createdAt, LocalDate createdAt2);
    List<PostEntity> findByIsReleasedTrue();
}