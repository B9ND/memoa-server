package org.example.memoaserver.domain.post.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TagDTO {
    private String tag;

    private String name;

    private Long postId;
}
