package org.example.memoaserver.domain.image.dto.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@ApiModel(description = "이미지 url 모델")
public class ImageResponse {
    @ApiModelProperty(name = "이미지 url")
    private String url;
}
