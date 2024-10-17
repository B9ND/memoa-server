package org.example.memoaserver.domain.user.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel(description = "토큰 재발급 모델")
public class RefreshTokenRequest {
    @ApiModelProperty(value = "refresh 토큰", example = "haafafdfadsdfasdf.asdf.adsf.qw3qrf2f2qdfq34fasd.fgaesfg.sawsdfadf")
    private String refresh;
}