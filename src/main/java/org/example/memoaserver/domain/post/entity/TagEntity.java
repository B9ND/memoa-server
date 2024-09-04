package org.example.memoaserver.domain.post.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Builder
    public TagEntity(Long id, String name, Long postId) {
        this.id = id;
        this.name = name;
        this.postId = postId;
    }
}
