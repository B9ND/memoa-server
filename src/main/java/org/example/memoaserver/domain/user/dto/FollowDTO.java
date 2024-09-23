package org.example.memoaserver.domain.user.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FollowDTO {
    private Long following;
    private Long follower;
}
