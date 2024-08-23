package org.example.memoaserver.domain.post.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.memoaserver.domain.user.entity.UserEntity;

import java.util.List;

@Getter @Setter
public class PostDTO {
    private Long id;

    private String title;

    private String content;

    private UserEntity user;

    private List<TagDTO> tags;

    private Boolean isReleased;
}
