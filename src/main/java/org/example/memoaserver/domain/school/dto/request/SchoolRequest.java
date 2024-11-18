package org.example.memoaserver.domain.school.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.memoaserver.domain.school.dto.DepartmentDTO;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class SchoolRequest {
    private String name;
    private List<DepartmentDTO> departments;
}
