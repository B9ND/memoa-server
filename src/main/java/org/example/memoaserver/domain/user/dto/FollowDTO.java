package org.example.memoaserver.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FollowDTO {
    private Long following;
    private Long follower;
}
