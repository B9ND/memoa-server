package org.example.memoaserver.domain.bookmark.dto.res;

import lombok.Builder;
import lombok.Getter;
import org.example.memoaserver.domain.bookmark.entity.BookmarkEntity;

import java.time.LocalDate;

@Getter
@Builder
public class BookmarkResponse {
    private String nickname;

    private Long postId;

    private LocalDate createdAt;

    public static BookmarkResponse fromBookmarkEntity(BookmarkEntity bookmarkEntity) {
        return BookmarkResponse.builder()
                .nickname(bookmarkEntity.getUser().getNickname())
                .postId(bookmarkEntity.getPost().getPost_id())
                .createdAt(bookmarkEntity.getCreatedAt())
                .build();
    }
}
