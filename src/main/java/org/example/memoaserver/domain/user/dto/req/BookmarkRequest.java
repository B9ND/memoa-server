package org.example.memoaserver.domain.user.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookmarkRequest {
    private Long userId;
    private Long postId;

    public BookmarkRequest(Long userId, Long postId) {
        this.postId = postId;
        this.userId = userId;
    }
}
