package org.example.memoaserver.domain.report.repository;

import org.example.memoaserver.domain.post.entity.PostEntity;
import org.example.memoaserver.domain.report.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, Long> {
    Optional<List<ReportEntity>> findByPost(PostEntity post);
}