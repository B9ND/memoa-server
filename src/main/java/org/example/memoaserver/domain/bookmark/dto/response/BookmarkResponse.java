package org.example.memoaserver.domain.bookmark.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.example.memoaserver.domain.bookmark.entity.BookmarkEntity;

import java.time.LocalDate;

@Getter
@Builder
public class BookmarkResponse {
    private String nickname;

    private Long postId;

    private String title;

    private String profileImage;

    private LocalDate createdAt;

    public static BookmarkResponse fromBookmarkEntity(BookmarkEntity bookmarkEntity) {
        return BookmarkResponse.builder()
                .nickname(bookmarkEntity.getPost().getUser().getNickname())
                .postId(bookmarkEntity.getPost().getPost_id())
                .title(bookmarkEntity.getPost().getTitle())
                .profileImage(bookmarkEntity.getPost().getUser().getProfileImage())
                .createdAt(bookmarkEntity.getCreatedAt())
                .build();
    }
}
