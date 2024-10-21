package org.example.memoaserver.domain.post.dto.req;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(name = "게시물 검색 모델")
public class SearchPostRequest {
    @Schema(description = "검색어", example = "바인드")
    private String search;
    private List<String> tags;

    @Schema(description = "받을 페이지 (무한로딩시 1씩 추가)", example = "0")
    private Integer page = 0;

    @Schema(description = "받을 게시물 갯수", example = "10")
    private Integer size = 10;
}
