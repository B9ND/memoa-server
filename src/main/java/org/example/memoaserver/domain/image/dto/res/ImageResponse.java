package org.example.memoaserver.domain.image.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "이미지 url 모델")
public class ImageResponse {
    @Schema(description = "이미지 url")
    private String url;
}
