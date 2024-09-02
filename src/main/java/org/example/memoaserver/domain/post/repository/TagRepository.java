package org.example.memoaserver.domain.post.repository;

import org.example.memoaserver.domain.post.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository extends JpaRepository<TagEntity, Long> {
    List<TagEntity> findByPostId(Long postId);
}

