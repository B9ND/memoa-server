package org.example.memoaserver.domain.post.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "tag")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long postId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "post_id", nullable = false)
//    private PostEntity post;

    @Builder
    public TagEntity(Long id, String name, Long postId) {
        this.id = id;
        this.name = name;
        this.postId = postId;
    }
}
