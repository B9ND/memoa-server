package org.example.memoaserver.domain.post.dto.res;

import lombok.Builder;
import lombok.Getter;
import org.example.memoaserver.domain.post.entity.ImageEntity;
import org.example.memoaserver.domain.post.entity.PostEntity;
import org.example.memoaserver.domain.post.entity.TagEntity;
import org.example.memoaserver.domain.user.entity.UserEntity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder
public class PostResponse {
    private Long id;

    private String title;

    private String content;

    private String author;

    private Set<String> tags;

    private List<String> images;

    public static PostEntity of(PostResponse postDTO, UserEntity user, Set<TagEntity> tags, List<ImageEntity> images) {
        return PostEntity.builder()
                .user(user)
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .tags(tags)
                .images(images)
                .build();
    }

    public static PostResponse fromPostEntity(PostEntity postEntity) {
        return PostResponse.builder()
                .id(postEntity.getPost_id())
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .author(postEntity.getUser().getNickname())
                .tags(postEntity.getTags().stream().map(TagEntity::getTagName).collect(Collectors.toSet()))
                .images(postEntity.getImages().stream().map(ImageEntity::getUrl).collect(Collectors.toList()))
                .build();
    }
}
