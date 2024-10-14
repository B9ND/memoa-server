package org.example.memoaserver.domain.post.dto.req;

import lombok.Getter;
import lombok.Setter;
import org.example.memoaserver.domain.post.entity.ImageEntity;
import org.example.memoaserver.domain.post.entity.PostEntity;
import org.example.memoaserver.domain.post.entity.TagEntity;
import org.example.memoaserver.domain.user.entity.UserEntity;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class PostReq {
    private String title;
    private String content;

    @Setter
    private Set<String> tags;

    private List<String> images;

    private Boolean isReleased;

    public PostEntity toPostEntity(UserEntity user, Set<TagEntity> tags) {
        PostEntity post = PostEntity.builder()
                .title(title)
                .tags(tags)
                .content(content)
                .isReleased(isReleased)
                .user(user)
                .build();


        return post;
    }
}
