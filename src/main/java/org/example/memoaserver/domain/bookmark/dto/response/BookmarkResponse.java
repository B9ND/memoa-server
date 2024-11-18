package org.example.memoaserver.domain.bookmark.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.example.memoaserver.domain.bookmark.entity.BookmarkEntity;
import org.example.memoaserver.domain.post.entity.ImageEntity;
import org.example.memoaserver.domain.post.entity.TagEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder
public class BookmarkResponse {
    private String nickname;

    private Long postId;

    private String title;

    private List<String> images;

    private Set<String> tags;

    private String profileImage;

    private LocalDate createdAt;

    public static BookmarkResponse fromBookmarkEntity(BookmarkEntity bookmarkEntity) {
        return BookmarkResponse.builder()
                .nickname(bookmarkEntity.getPost().getUser().getNickname())
                .postId(bookmarkEntity.getPost().getPost_id())
                .title(bookmarkEntity.getPost().getTitle())
                .images(fromImageEntities(bookmarkEntity.getPost().getImages()))
                .profileImage(bookmarkEntity.getPost().getUser().getProfileImage())
                .tags(fromTagEntities(bookmarkEntity.getPost().getTags()))
                .createdAt(bookmarkEntity.getCreatedAt())
                .build();
    }

    private static Set<String> fromTagEntities(Set<TagEntity> tagEntities) {
        return tagEntities
            .stream()
            .map(TagEntity::fromTagEntity)
            .collect(Collectors.toSet());
    }

    private static List<String> fromImageEntities(List<ImageEntity> imageEntities) {
        return imageEntities
            .stream()
            .map(ImageEntity::fromImageEntity)
            .toList();
    }
}
