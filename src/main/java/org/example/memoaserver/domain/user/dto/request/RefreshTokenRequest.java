package org.example.memoaserver.domain.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "토큰 재발급 모델")
public class RefreshTokenRequest {
    @Schema(description = "refresh 토큰", example = "haafafdfadsdfasdf.asdf.adsf.qw3qrf2f2qdfq34fasd.fgaesfg.sawsdfadf")
    private String refresh;
}