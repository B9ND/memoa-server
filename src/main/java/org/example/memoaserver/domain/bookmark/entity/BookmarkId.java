package org.example.memoaserver.domain.bookmark.entity;

import java.io.Serializable;
import java.util.Objects;

public class BookmarkId implements Serializable {
    private Long user;
    private Long post;

    public BookmarkId() {}

    public BookmarkId(Long user, Long post) {
        this.user = user;
        this.post = post;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookmarkId that = (BookmarkId) o;
        return Objects.equals(user, that.user) && Objects.equals(post, that.post);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, post);
    }
}
