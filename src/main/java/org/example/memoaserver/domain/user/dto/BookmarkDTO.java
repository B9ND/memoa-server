package org.example.memoaserver.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.memoaserver.domain.user.entity.BookmarkEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkDTO {
    private String message;
    private boolean isExist;

    public static BookmarkDTO createBookmarkDTO(String message, BookmarkEntity bookmark) {
        return new BookmarkDTO(
                message,
                bookmark.isExist()
        );
    }
}
