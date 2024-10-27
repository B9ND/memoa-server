package org.example.memoaserver.domain.bookmark.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.memoaserver.domain.post.entity.PostEntity;
import org.example.memoaserver.domain.user.entity.UserEntity;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "bookmark")
public class BookmarkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id", nullable = false)
    private Long bookmark_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    @Builder
    public BookmarkEntity(UserEntity user, PostEntity post) {
        this.user = user;
        this.post = post;
    }
}