package org.example.memoaserver.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.memoaserver.domain.post.entity.PostEntity;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Getter @Setter
@Entity(name="bookmark")
@AllArgsConstructor
@NoArgsConstructor
public class BookmarkEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PostEntity post;

    @Column(nullable = false)
    private boolean isExist;

    @Column
    private LocalDateTime createDate;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private BookmarkEntity bookmark;

//    public static BookmarkEntity createBookmark(User user, PostEntity post) {
//        return new BookmarkEntity(
//                (Long) null,
//                (UserEntity) user,
//                post,
//                true,
//                LocalDateTime.now()
//        );
//    }

    public void deleteBookmark(PostEntity post) {
        this.isExist = false;
    }
}
