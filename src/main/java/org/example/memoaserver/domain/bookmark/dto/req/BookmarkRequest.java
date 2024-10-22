package org.example.memoaserver.domain.bookmark.dto.req;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookmarkRequest {
    private Long userId;
    private Long postId;

    public BookmarkRequest(Long userId, Long postId) {
        this.postId = postId;
        this.userId = userId;
    }
}
