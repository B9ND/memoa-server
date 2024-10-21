package org.example.memoaserver.domain.school.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SchoolResponse {
    private Long school_id;

    private String name;
}
