package org.example.memoaserver.domain.bookmark.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.memoaserver.domain.user.entity.UserEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity(name = "bookmark")
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@IdClass(BookmarkId.class)
@Table(name = "bookmark", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "post_id"})
})
public class BookmarkEntity {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private org.example.memoaserver.domain.post.entity.PostEntity post;

    @CreatedDate
    private LocalDate createdAt;
}