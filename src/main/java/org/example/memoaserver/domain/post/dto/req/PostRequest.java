package org.example.memoaserver.domain.post.dto.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.memoaserver.domain.post.entity.PostEntity;
import org.example.memoaserver.domain.post.entity.TagEntity;
import org.example.memoaserver.domain.user.entity.UserEntity;

import java.util.List;
import java.util.Set;

@ApiModel(description = "게시물 저장 모델")
@Getter
public class PostRequest {
    @ApiModelProperty(value = "제목", example = "메모아")
    private String title;
    @ApiModelProperty(value = "내용", example = "메모아 상세 사용법")
    private String content;

    @ApiModelProperty(value = "태그", example = "국어, 사회, 수학, 영어")
    @Setter
    private Set<String> tags;

    @ApiModelProperty(value = "이미지들(미리보기)", example = "https://lorempicsum.photos/200")
    private List<String> images;

    @ApiModelProperty(value = "공개, 비공개 설정", example = "true")
    private Boolean isReleased;

    public PostEntity toPostEntity(UserEntity user, Set<TagEntity> tags) {
        PostEntity post = PostEntity.builder()
                .title(title)
                .tags(tags)
                .content(content)
                .isReleased(isReleased)
                .user(user)
                .build();


        return post;
    }
}
