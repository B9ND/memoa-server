package org.example.memoaserver.domain.school.dto.res;

import lombok.Builder;
import lombok.Getter;
import org.example.memoaserver.domain.school.entity.DepartmentEntity;

import java.util.List;

@Getter
@Builder
public class DepartmentDataResponse {
    private Long id;
    private String name;
    private Integer grade;
    private List<String> subjects;

    public static DepartmentDataResponse fromDepartmentEntity(DepartmentEntity departmentEntity) {
        return DepartmentDataResponse.builder()
                .id(departmentEntity.getId())
                .name(departmentEntity.getName())
                .grade(departmentEntity.getGrade())
                .subjects(departmentEntity.getSubjects())
                .build();
    }
}
