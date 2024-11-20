package org.example.memoaserver.domain.school.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class DepartmentRequest {
    private String name;
    private List<String> subjects;
    private Integer grade;
}
