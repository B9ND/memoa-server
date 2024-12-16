package org.example.memoaserver.domain.post.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity(name = "image")
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long image_id;

    private String url;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    public static String fromImageEntity(ImageEntity imageEntity) {
        return imageEntity.getUrl();
    }

    @Builder
    public ImageEntity(Long image_id, String url, PostEntity post) {
        this.image_id = image_id;
        this.url = url;
        this.post = post;
    }
}
