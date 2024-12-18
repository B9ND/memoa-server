package org.example.memoaserver.domain.post.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.example.memoaserver.domain.post.entity.ImageEntity;
import org.example.memoaserver.domain.post.entity.PostEntity;
import org.example.memoaserver.domain.post.entity.TagEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private String authorProfileImage;

    private Set<String> tags;

    private LocalDateTime createdAt;

    private List<String> images;

    private Boolean isBookmarked;

    public static PostResponse fromPostEntity(PostEntity postEntity, Boolean isBookmarked) {
        return PostResponse.builder()
                .id(postEntity.getPost_id())
                .title(postEntity.getTitle())
                .content(postEntity.getContent())
                .author(postEntity.getUser().getNickname())
                .authorProfileImage(postEntity.getUser().getProfileImage())
                .isBookmarked(isBookmarked)
                .tags(postEntity.getTags().stream().map(TagEntity::getTagName).collect(Collectors.toSet()))
                .createdAt(postEntity.getCreatedAt())
                .images(postEntity.getImages().stream().map(ImageEntity::getUrl).collect(Collectors.toList()))
                .build();
    }
}
