package org.example.memoaserver.domain.school.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.memoaserver.domain.school.dto.request.DepartmentRequest;

import java.util.List;

@Entity(name = "department")
@Getter @Setter
@NoArgsConstructor
public class DepartmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer grade;

    @ManyToOne
    @JoinColumn(name = "school_entity_id", nullable = false)
    private SchoolEntity schoolEntity;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> subjects;

    @Builder
    public DepartmentEntity(String name, Integer grade, SchoolEntity schoolEntity, List<String> subjects) {
        this.name = name;
        this.grade = grade;
        this.schoolEntity = schoolEntity;
        this.subjects = subjects;
    }

    public static DepartmentEntity fromDepartmentRequest(DepartmentRequest departmentRequest, SchoolEntity schoolEntity) {
        return DepartmentEntity.builder()
                .name(departmentRequest.getName())
                .grade(departmentRequest.getGrade())
                .subjects(departmentRequest.getSubjects())
                .schoolEntity(schoolEntity)
                .build();
    }
}
