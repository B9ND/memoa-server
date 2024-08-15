package org.example.memoaserver.domain.school.repository;

import org.example.memoaserver.domain.school.entity.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchoolRepository extends JpaRepository<SchoolEntity, Long> {
    List<SchoolEntity> findByNameContainingIgnoreCase(String name);
}
