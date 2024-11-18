package org.example.memoaserver.domain.school.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SchoolResponse {
    private Long school_id;

    private String name;
}
