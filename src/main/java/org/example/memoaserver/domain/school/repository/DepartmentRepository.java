package org.example.memoaserver.domain.school.repository;

import org.example.memoaserver.domain.school.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
}
