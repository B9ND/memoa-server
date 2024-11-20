package org.example.memoaserver.domain.school.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
public class SchoolRequest {
    private String name;
    private List<DepartmentRequest> departments;
}
