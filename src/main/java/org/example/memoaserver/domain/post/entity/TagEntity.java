package org.example.memoaserver.domain.post.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
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
}
