package org.example.memoaserver.domain.post.dto;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.memoaserver.domain.user.entity.UserEntity;

import java.time.LocalDate;
import java.util.List;

@ApiModel(description = "게시물 모델")
@Builder
@Getter @Setter
public class PostDTO {
    private Long id;

    private String title;

    private String content;

    private UserEntity user;

    private List<TagDTO> tags;

    private LocalDate createdAt;

    private Boolean isReleased;

}
