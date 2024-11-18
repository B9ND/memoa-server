package org.example.memoaserver.domain.post.dto.request;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SearchPostRequest {
    @Parameter(description = "검색어", required = false, example = "바인드")
    private String search;
    @Parameter(description = "태그들", example = "국어")
    private List<String> tags;

    @Parameter(description = "받을 페이지 (무한로딩시 1씩 추가)", example = "0")
    private Integer page = 0;

    @Parameter(description = "받을 게시물 갯수", example = "10")
    private Integer size = 10;
}
