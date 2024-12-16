package org.example.memoaserver.domain.post.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "tag")
public class TagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tag_id;

    @Column(unique = true, nullable = false)
    private String tagName;

    public static String fromTagEntity(TagEntity tagEntity) {
        return tagEntity.tagName;
    }

    @Builder
    public TagEntity(Long tag_id, String tagName) {
        this.tag_id = tag_id;
        this.tagName = tagName;
    }
}
