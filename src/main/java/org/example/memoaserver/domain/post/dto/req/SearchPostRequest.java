package org.example.memoaserver.domain.post.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "게시물 검색 모델")
public class SearchPostRequest {
    private String search;

    private List<String> tags;

    private Integer page = 0;

    private Integer size = 10;
}
