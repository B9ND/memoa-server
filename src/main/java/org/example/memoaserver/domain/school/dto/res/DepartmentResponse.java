package org.example.memoaserver.domain.school.dto.res;

import lombok.Builder;
import lombok.Getter;
import org.example.memoaserver.domain.school.entity.DepartmentEntity;
import org.example.memoaserver.domain.school.entity.SchoolEntity;

import java.util.List;

@Getter
@Builder
public class DepartmentResponse {
    private String name;

    private Integer grade;

    private SchoolResponse school;

    private List<String> subjects;

    public static DepartmentResponse fromDepartmentEntity(DepartmentEntity departmentEntity) {
        return DepartmentResponse.builder()
                .name(departmentEntity.getName())
                .grade(departmentEntity.getGrade())
                .school(getSchool(departmentEntity.getSchoolEntity()))
                .subjects(departmentEntity.getSubjects())
                .build();
    }

    private static SchoolResponse getSchool(SchoolEntity schoolEntity) {
        return SchoolResponse.builder()
                .name(schoolEntity.getName())
                .school_id(schoolEntity.getId())
                .build();
    }
}
