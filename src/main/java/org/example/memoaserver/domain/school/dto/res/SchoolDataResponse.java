package org.example.memoaserver.domain.school.dto.res;

import lombok.Builder;
import lombok.Getter;
import org.example.memoaserver.domain.school.entity.SchoolEntity;

import java.util.List;

@Getter
@Builder
public class SchoolDataResponse {
    private String name;
    private List<DepartmentDataResponse> departments;

    public static SchoolDataResponse fromSchoolEntity(SchoolEntity schoolEntity) {
        return SchoolDataResponse.builder()
                .name(schoolEntity.getName())
                .departments(schoolEntity.getDepartments().stream().map(DepartmentDataResponse::fromDepartmentEntity).toList())
                .build();
    }
}
