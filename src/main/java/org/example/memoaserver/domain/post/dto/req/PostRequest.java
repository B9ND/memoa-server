package org.example.memoaserver.domain.post.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.example.memoaserver.domain.post.entity.ImageEntity;
import org.example.memoaserver.domain.post.entity.PostEntity;
import org.example.memoaserver.domain.post.entity.TagEntity;
import org.example.memoaserver.domain.user.entity.UserEntity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Schema(name = "게시물 저장 모델")
@Getter
public class PostRequest {
    @Schema(description = "제목", example = "바인드")
    private String title;
    @Schema(description = "내용", example = "메모아 상세 사용법")
    private String content;

    @Schema(description = "태그")
    @Setter
    private Set<String> tags;

    @Schema(description = "이미지들(미리보기)", example = "[\"https://lorempicsum.photos/200\"]")
    private List<String> images;

    @Schema(description = "공개, 비공개 설정", example = "true")
    private Boolean isReleased;

    public PostEntity toPostEntity(UserEntity user, Set<TagEntity> tags) {
        return PostEntity.builder()
                .title(title)
                .tags(tags)
                .content(content)
                .isReleased(isReleased)
                .user(user)
                .build();
    }

    public List<ImageEntity> toImageEntities(PostEntity post) {
        return images.stream()
                .map(imageUrl -> ImageEntity.builder()
                        .url(imageUrl)
                        .post(post)
                        .build())
                .collect(Collectors.toList());
    }
}
